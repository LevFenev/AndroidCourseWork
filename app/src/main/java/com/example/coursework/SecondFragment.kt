package com.example.coursework

import android.annotation.SuppressLint
import android.content.Context
import android.os.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.coursework.databinding.FragmentSecondBinding
import kotlin.math.floor

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = (activity as MainActivity)
        val workTime = activity.workTime
        val breakTime = activity.breakTime
        var timerMessage = "Unknown Error"

        val res = resources // для работы стрингов

        val breakTimer = object : CountDownTimer((breakTime * 60 * 1000).toLong(), 1000) {
            override fun onTick(p0: Long) {
                view.findViewById<TextView>(R.id.textview_timer).text = (p0 / 1000).toString()
            }

            override fun onFinish() {
                // сообщить об окончании перерыва
                val breakTimerDone = String.format(res.getString(R.string.advice_hugeBreak))
                view.findViewById<TextView>(R.id.textview_second).text = breakTimerDone
            }
        }

        val workTimer = object : CountDownTimer((workTime * 60 * 1000).toLong(), 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(p0: Long) {
                val hours = floor(((p0 / 1000) / 3600).toFloat()).toInt().toString()
                var minutes = floor((((p0 / 1000) % 3600) / 60).toFloat()).toInt().toString()
                if (minutes.length < 2) {
                    minutes = "0$minutes"
                }
                var seconds = floor(((p0 / 1000) % 60).toFloat()).toInt().toString()
                if (seconds.length < 2) {
                    seconds = "0$seconds"
                }
                view.findViewById<TextView>(R.id.textview_timer).text = "$hours:$minutes:$seconds"
            }

            @RequiresApi(Build.VERSION_CODES.S)
            override fun onFinish() {
                // сообщить об окончании работы
                val workTimerDone = String.format(res.getString(R.string.workTimer_done))
                view.findViewById<TextView>(R.id.textview_second).text = workTimerDone
                breakTimer.start()

                // провибрировать
                val vibratorManager =
                    (getActivity()?.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager)
                val v = vibratorManager.defaultVibrator
                v.vibrate(500)
            }
        }
//        workTimer.start()

        val ratio = workTime / breakTime.toFloat()
// проверка "качества" времени
        if (ratio < 5) {
            val hugeBreak = String.format(res.getString(R.string.advice_hugeBreak))
            timerMessage = hugeBreak
            workTimer.start()
        } else if (ratio >= 8) {
            val tinyBreak = String.format(res.getString(R.string.advice_tinyBreak))
            timerMessage = tinyBreak
            workTimer.start()
        } else {
            val timerStarts = String.format(res.getString(R.string.workTimer_start))
            timerMessage = timerStarts
            workTimer.start()
        }

        if (workTime > 24 * 60) {
            val massiveWork = String.format(res.getString(R.string.advice_massiveWork))
            timerMessage += massiveWork
            workTimer.start()
        }
        if (workTime > 24 * 60) {
            val massiveBreak = String.format(res.getString(R.string.advice_massiveBreak))
            timerMessage += massiveBreak
            workTimer.start()
        }

        view.findViewById<TextView>(R.id.textview_second).text = timerMessage

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}