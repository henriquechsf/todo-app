package tech.henriquedev.todoapp.fragments.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import tech.henriquedev.todoapp.R
import tech.henriquedev.todoapp.data.model.TodoData
import tech.henriquedev.todoapp.data.viewmodel.TodoViewModel
import tech.henriquedev.todoapp.databinding.FragmentUpdateBinding
import tech.henriquedev.todoapp.fragments.SharedViewModel

class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    // safeArgs
    private val args by navArgs<UpdateFragmentArgs>()

    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mTodoViewModel: TodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set Menu
        setHasOptionsMenu(true)

        binding.edtCurrentTitle.setText(args.curremtItem.title)
        binding.edtCurrentDescription.setText(args.curremtItem.description)
        binding.spinCurrentPriorities.setSelection(mSharedViewModel.parsePriorityToInt(args.curremtItem.priority))
        binding.spinCurrentPriorities.onItemSelectedListener = mSharedViewModel.listener
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.udpate_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        val title = binding.edtCurrentTitle.text.toString()
        val description = binding.edtCurrentDescription.text.toString()
        val getPriority = binding.spinCurrentPriorities.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(title, description)
        if (validation) {
            val updatedItem = TodoData(
                args.curremtItem.id,
                title,
                mSharedViewModel.parsePriority(getPriority),
                description
            )

            mTodoViewModel.updateData(updatedItem)
            Toast.makeText(requireContext(), "Successfully updated", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Sim") {_, _ ->
            mTodoViewModel.deleteItem(args.curremtItem)
            Toast.makeText(requireContext(), "Successfully Removed", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("Não") {_, _ ->}
        builder.setTitle("Remover ${args.curremtItem.title}?")
        builder.setMessage("Tem certeza que deseja remover ${args.curremtItem.title}?")
        builder.create().show()
    }
}