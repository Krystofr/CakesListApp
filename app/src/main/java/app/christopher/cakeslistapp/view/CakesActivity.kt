package app.christopher.cakeslistapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.christopher.cakeslistapp.OnItemClickListener
import app.christopher.cakeslistapp.network.CakesRetrofitInstance
import app.christopher.cakeslistapp.adapter.CakesAdapter
import app.christopher.cakeslistapp.toastMessage
import cakeslistapp.databinding.ActivityCakesBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.item_cakes.*
import retrofit2.HttpException
import java.io.IOException
import java.util.*

private const val TAG = "MainActivity"

class CakesActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var binding : ActivityCakesBinding
    private var cakesAdapter = CakesAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCakesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        //Make the Api request on a background thread using KT Coroutines
        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true //As long as we make the Api request, our progress bar should be visible.
            val response = try {
                CakesRetrofitInstance.API.getCakes() //Coroutine scope that is lifecycle aware
            } catch (ex: IOException){
                Log.d(TAG, "IOException: You might not be connected to the internet")
                Log.e(TAG, ex.localizedMessage!!)
                toastMessage(ex.localizedMessage!!)
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            } catch (e: HttpException){
                Log.d(TAG, "HttpException: Unexpected response")
                Log.e(TAG, e.localizedMessage!!)
                toastMessage(e.localizedMessage!!)
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null){
                cakesAdapter.cakesModels = response.body()!!
            } else {
                Log.d(TAG, "Response not successful")
                toastMessage("Response not successful")
                binding.progressBar.isVisible = false
            }
            binding.progressBar.isVisible = false // Either way, successful or not, we want to hide the progressbar again.
        }
    }

    private fun setupRecyclerView() = binding.rvCakes.apply {

        //cakeList.sort()  // TODO: 02/12/2021 Comparator in CakesModel should be implemented to perform this sort (by passing the Comparator object as parameter)
        cakesAdapter = CakesAdapter(this@CakesActivity)
        adapter = cakesAdapter
        layoutManager = LinearLayoutManager(this@CakesActivity)
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener(){
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                return false
            }
        })
        //Pulldown to refresh
        binding.swipeRL.setOnRefreshListener {
            //Refreshing /* RQ.3: Provide some kind of refresh option that reloads the list */
            refreshRV()
            binding.swipeRL.isRefreshing = false
        }
    }

    override fun onItemClick(position: Int) {
        //val cakes = cakesAdapter.cakesModels[position]

       // cake_description.text = cakes.desc
        toastMessage("Cake $position clicked")
        cakesAdapter.getItemId(position)
        MaterialAlertDialogBuilder(this)
            .setCancelable(true)
            .setMessage("${cake_description.text}")
            .setPositiveButton("OKAY") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
        cakesAdapter.notifyItemChanged(position)
    }
    private fun refreshRV(){
        Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, CakesActivity::class.java))
        overridePendingTransition(0, 0)
        finish()
        overridePendingTransition(0, 0)
    }
}