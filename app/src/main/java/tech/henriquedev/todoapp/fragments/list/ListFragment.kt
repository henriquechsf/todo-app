package tech.henriquedev.todoapp.fragments.list

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import tech.henriquedev.todoapp.R
import tech.henriquedev.todoapp.data.viewmodel.TodoViewModel
import tech.henriquedev.todoapp.databinding.FragmentListBinding
import tech.henriquedev.todoapp.fragments.SharedViewModel

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val mTodoViewModel: TodoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private val listAdapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set Menu
        setHasOptionsMenu(true)

        binding.recyclerView.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }

        mTodoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaEmpty(data)
            listAdapter.setData(data)
        })

        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseViews(it)
        })

        binding.fabCreate.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        binding.listLayout.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all) {
            confirmRemoval()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Sim") { _, _ ->
            mTodoViewModel.deleteAll()
            Toast.makeText(requireContext(), "Successfully Removed Everything", Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNegativeButton("Não") { _, _ -> }
        builder.setTitle("Remover Tudo?")
        builder.setMessage("Tem certeza que deseja remover todas tarefas?")
        builder.create().show()
    }

    private fun showEmptyDatabaseViews(emptyDatabase: Boolean) {
        if (emptyDatabase) {
            binding.imageNoData.visibility = View.VISIBLE
            binding.textNodata.visibility = View.VISIBLE
        } else {
            binding.imageNoData.visibility = View.INVISIBLE
            binding.textNodata.visibility = View.INVISIBLE
        }
    }
}