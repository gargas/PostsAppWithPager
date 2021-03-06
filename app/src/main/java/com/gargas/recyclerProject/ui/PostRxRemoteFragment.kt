package com.gargas.recyclerProject.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gargas.recyclerProject.Injection
import com.gargas.recyclerProject.R
import com.gargas.recyclerProject.activities.ui.main.MainFragment
import com.gargas.recyclerProject.databinding.FragmentPostsListBinding
import com.gargas.recyclerProject.ui.adapter.PostsRxAdapter
import com.gargas.recyclerProject.ui.viewmodels.GetPostsRxViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.ExperimentalCoroutinesApi

class PostRxRemoteFragment : Fragment() {
    private val mDisposable = CompositeDisposable()

    private lateinit var mBinding: FragmentPostsListBinding
    private lateinit var mViewModel: GetPostsRxViewModel
    private lateinit var mAdapter: PostsRxAdapter

    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentPostsListBinding.inflate(inflater, container, false)

        val view = mBinding.root

//        activity?.title = getString(R.string.rx_with_remote_mediator)

        mViewModel = ViewModelProvider(this, Injection.provideRxRemoteViewModel(view.context)).get(
            GetPostsRxViewModel::class.java)

        mAdapter = PostsRxAdapter()

        mBinding.list.layoutManager = LinearLayoutManager(view.context)
        mBinding.list.adapter = mAdapter
        mBinding.list.adapter = mAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter()
        )
        mAdapter.addLoadStateListener { loadState ->
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            errorState?.let {
                AlertDialog.Builder(view.context)
                    .setTitle(R.string.error)
                    .setMessage(it.error.localizedMessage)
                    .setNegativeButton(R.string.cancel) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton(R.string.retry) { _, _ ->
                        mAdapter.retry()
                    }
                    .show()
            }
        }

        mDisposable.add(mViewModel.getPosts().subscribe {
            mAdapter.submitData(lifecycle, it)
        })

        return view
    }

    override fun onDestroyView() {
        mDisposable.dispose()

        super.onDestroyView()
    }

    companion object {
        fun newInstance() = PostRxRemoteFragment()
    }

}