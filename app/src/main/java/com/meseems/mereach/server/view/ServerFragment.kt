package com.meseems.mereach.server.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import com.meseems.mereach.R
import com.meseems.mereach.base.BaseDialogFragment
import com.meseems.mereach.server.di.ServerModule.Companion.SERVER_VIEW_MODEL
import com.meseems.mereach.utils.annotation.InjectVM
import com.meseems.mereach.utils.extensions.collections.getTyped

@InjectVM(SERVER_VIEW_MODEL)
class ServerFragment : BaseDialogFragment() {

    lateinit var dialogView: View

    private val viewModel by lazy {
        viewModels.getTyped<ServerViewModel>(SERVER_VIEW_MODEL)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return buildDialog()
    }

    private fun buildDialog() : Dialog {

        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.new_server_title)
        builder.setView(createInputView())
        builder.setPositiveButton(R.string.all_button_save) { _, _ ->
            onClickPositiveButton()
        }
        builder.setNegativeButton(R.string.all_button_cancel) { _, _ ->
            dismiss()
        }

        return builder.create()
    }


    override fun onResume() {
        super.onResume()
        subscribeObservables()
    }

    private fun subscribeObservables() {
        subscribe(

        )
    }

    private fun onClickPositiveButton() {
        val name = dialogView.findViewById<EditText>(R.id.edittext_new_server_name).text.toString()
        val url = dialogView.findViewById<EditText>(R.id.edittext_new_server_url).text.toString()
        saveServer(name, url)
    }

    private fun saveServer(name: String, url: String) {
        subscribe(viewModel.saveServer(name, url).subscribe{
            dismiss()
        })
    }

    private fun createInputView() : View {
        val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        dialogView = inflater.inflate(R.layout.fragment_new_server, null, false)
        return dialogView
    }

    companion object {

        fun newInstance(): ServerFragment {
            return ServerFragment()
        }

    }
}
