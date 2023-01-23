package com.example.coursework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coursework.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        /*val rootView = inflater.inflate(R.layout.fragment_first, container, false);
        val activity = (activity as MainActivity)

        if (activity.workTime != 0) { // если ноль, можно это значение не запоминать
            rootView.findViewById<EditText>(R.id.editTextWorkTime).setText(activity.workTime)
        }
        if (activity.breakTime != 0) {
            rootView.findViewById<EditText>(R.id.editTextBreakTime).setText(activity.breakTime)
        }*/

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = (activity as MainActivity)

        binding.editTextWorkTime.setText(activity.workTime.toString())
        binding.editTextBreakTime.setText(activity.breakTime.toString())

//        var workTime = activity.workTime
//        var breakTime = activity.breakTime
        var warning = "Unknown Error"

        binding.buttonFirst.setOnClickListener {
            val workTime = view.findViewById<EditText>(R.id.editTextWorkTime).text.toString()
            val breakTime = view.findViewById<EditText>(R.id.editTextBreakTime).text.toString()

            val res = resources // это название файла xml

            if (workTime == "") {

                val blankWork = String.format(res.getString(R.string.warning_blankWork))
                warning = blankWork
                view.findViewById<TextView>(R.id.textView_Warning).text = warning

            } else if (breakTime == "") {

                val blankBreak = String.format(res.getString(R.string.warning_blankBreak))
                warning = blankBreak
                view.findViewById<TextView>(R.id.textView_Warning).text = warning

            } else {

                activity.workTime = workTime.toInt()
                activity.breakTime = breakTime.toInt()

                if (activity.workTime == 0) {
                    val zeroWork = String.format(res.getString(R.string.warning_zeroWork))
                    warning = zeroWork
                    view.findViewById<TextView>(R.id.textView_Warning).text = warning
                } else if (activity.breakTime == 0) {
                    val zeroBreak = String.format(res.getString(R.string.warning_zeroBreak))
                    warning = zeroBreak
                    view.findViewById<TextView>(R.id.textView_Warning).text = warning
                } else {
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}