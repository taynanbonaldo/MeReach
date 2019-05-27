package com.meseems.mereach.base

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.meseems.mereach.R
import com.meseems.mereach.utils.annotation.InjectVM
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.kodein.di.android.kodein
import org.kodein.di.generic.provider

abstract class BaseDialogFragment : DialogFragment() {

    val kodein by kodein { requireContext() }

    private val subscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
    }

    override fun onStart() {
        super.onStart()
        subscribeVMObservables()
    }

    fun subscribe(disposable: Disposable): Disposable {
        subscriptions.add(disposable)
        return disposable
    }

    fun subscribe(vararg disposable: Disposable): Array<out Disposable> {
        subscriptions.addAll(*disposable)
        return disposable
    }

    override fun onStop() {
        super.onStop()
        subscriptions.clear()
    }

    protected var viewModels: MutableMap<String, BaseViewModel> = HashMap()

    private fun inject() {
        val injectVM: InjectVM? = javaClass.annotations.firstOrNull { it is InjectVM } as InjectVM?
        injectVM?.let {
            for (tag in it.tag) {
                val provider: () -> BaseViewModel by kodein.provider(tag = tag)
                val viewModel = provider.invoke()
                viewModels[tag] = viewModel
            }
        }
    }


    private fun subscribeVMObservables(){
        for (viewModel in viewModels.values) {
            processVM(viewModel)
        }
    }

    protected open fun <T : BaseViewModel> processVM(vm: T) {
        subscribe(
            vm.loadingObservable.subscribe { showLoading(it) },
            vm.externalErrorObservable.subscribe {
                onExternalError(
                    if(it.isBlank()) getString(R.string.all_message_an_error_occurred_generic)
                    else it)
            },
            vm.internalErrorObservable.subscribe { onInternalError(it) }
        )
    }

    protected open fun onExternalError(error: String) {

    }

    protected open fun onInternalError(resMessage: Int) {

    }

    protected open fun showLoading(show: Boolean) {

    }


}
