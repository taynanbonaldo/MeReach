package com.meseems.mereach.server.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meseems.mereach.R
import com.meseems.mereach.base.BaseMvvmActivity
import com.meseems.mereach.databinding.ActivityMainBinding
import com.meseems.mereach.server.di.ServerModule.Companion.SERVER_VIEW_MODEL
import com.meseems.mereach.server.domain.model.Server
import com.meseems.mereach.utils.annotation.InjectVM
import com.meseems.mereach.utils.extensions.collections.getTyped
import com.meseems.mereach.utils.extensions.rx.applyIoToMainSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

@InjectVM(SERVER_VIEW_MODEL)
class ServersActivity : BaseMvvmActivity() {

    private val serversViewModel by lazy {
        viewModels.getTyped<ServerViewModel>(SERVER_VIEW_MODEL)
    }

    private val adapter: ServersAdapter by lazy {
        ServersAdapter(serversViewModel.serverList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil
            .setContentView(this, R.layout.activity_main)
        binding.viewModel = serversViewModel

        initUi()
    }

    override fun onResume() {
        super.onResume()
        bindViewModel()
    }

    override fun initUi() {
        super.initUi()
        setSupportActionBar(toolbar)
        setupList()
    }

    private fun setupList() {
        recyclerview_main.adapter = adapter
        recyclerview_main.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    private fun bindViewModel() {
        subscribe(
            subscribeLoadServers(),
            subscribeAddNewServer(),
            subscribeConfirmServerRemoval(),
            subscribeReachabilityRefresher()
        )
    }

    private fun subscribeReachabilityRefresher(): Disposable =
        serversViewModel.reachabilityRefresher()
            .applyIoToMainSchedulers()
            .subscribe()

    private fun subscribeLoadServers(): Disposable =
        serversViewModel.getServers().subscribe()

    private fun subscribeAddNewServer(): Disposable =
        serversViewModel.addNewServerObservable
            .subscribe {
                showDialogNewServer()
            }

    private fun showDialogNewServer() {
        ServerFragment.newInstance()
            .show(supportFragmentManager, ServerFragment::class.java.name)
    }

    private fun subscribeConfirmServerRemoval(): Disposable =
        serversViewModel.askToRemoveServerObservable
            .subscribe(this::showDialogToConfirmServerRemoval)

    private fun showDialogToConfirmServerRemoval(server: Server) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.remove_server_title)
            .setMessage(getString(R.string.remove_server_message, server.name))
            .setCancelable(false)
            .setPositiveButton(R.string.all_button_ok) { _, _ ->
                confirmServerRemoval(server)
            }
            .setNegativeButton(R.string.all_button_cancel) { _, _ -> }
        val alert = builder.create()
        alert.show()
    }

    private fun confirmServerRemoval(server: Server) {
        subscribe(
            serversViewModel.deleteServer(server)
                .subscribe {  }
        )
    }
}
