package tech.henriquedev.todoapp.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import tech.henriquedev.todoapp.R
import tech.henriquedev.todoapp.data.model.TodoData
import tech.henriquedev.todoapp.data.viewmodel.TodoViewModel
import tech.henriquedev.todoapp.databinding.FragmentAddBinding
import tech.henriquedev.todoapp.fragments.SharedViewModel

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val mTodoViewModel: TodoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set Menu
        setHasOptionsMenu(true)

        binding.spinPriorities.onItemSelectedListener = mSharedViewModel.listener
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val mTitle = binding.edtTitle.text.toString()
        val mPriority = binding.spinPriorities.selectedItem.toString()
        val mDescription = binding.edtDescription.text.toString()

        val validation = mSharedViewModel.verifyDataFromUser(mTitle, mDescription)

        if (validation) {
            val newData = TodoData(
                0,
                mTitle,
                mSharedViewModel.parsePriority(mPriority),
                mDescription
            )

            mTodoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Succesfully added", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }
}