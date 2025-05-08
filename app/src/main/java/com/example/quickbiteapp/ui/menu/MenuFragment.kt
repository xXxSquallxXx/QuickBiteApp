package com.example.quickbiteapp.ui.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quickbiteapp.databinding.FragmentMenuBinding
import com.example.quickbiteapp.di.MainApplication
import com.example.quickbiteapp.ui.adapter.MenuAdapter
import kotlinx.coroutines.launch

class MenuFragment : Fragment() {

    var viewModel: MenuViewModel? = null
    var menuAdapter: MenuAdapter? = null

    private var binding: FragmentMenuBinding? = null
    private var restaurantId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel == null || menuAdapter == null) {
            val appComponent = (requireActivity().application as MainApplication).appComponent
            val factory = appComponent.menuFragmentFactory()
            viewModel = factory.viewModel
            menuAdapter = factory.menuAdapter
            Log.d("Test", "Зависимости повторно инициализированы в MenuFragment")
        }
        restaurantId = savedInstanceState?.getInt("restaurantId") ?: restaurantId
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        restaurantId = arguments?.getInt("restaurantId") ?: restaurantId
        Log.d("Test", "Загружаем меню для restaurantId: $restaurantId")
        menuAdapter?.setRestaurantId(restaurantId)
        viewModel?.loadMenu(restaurantId)
        observeMenu()
    }

    override fun onStart() {
        super.onStart()
        Log.d("Test", "onStart: Повторно загружаем меню для restaurantId: $restaurantId")
        viewModel?.loadMenu(restaurantId)
    }

    fun setRestaurantId(id: Int) {
        restaurantId = id
        menuAdapter?.setRestaurantId(restaurantId)
        Log.d("Test", "setRestaurantId: Загружаем меню для restaurantId: $restaurantId")
        viewModel?.loadMenu(restaurantId)
    }

    private fun setupRecyclerView() {
        binding?.menuList?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = menuAdapter
            Log.d("Test", "RecyclerView настроен, адаптер: $adapter")
        }
    }

    private fun observeMenu() {
        lifecycleScope.launch {
            viewModel?.menuItems?.collect { items ->
                Log.d("Test", "Получены данные меню: $items")
                menuAdapter?.submitList(items)
                if (items.isNotEmpty()) {
                    Log.d("Test", "Данные переданы в адаптер, количество элементов: ${items.size}")
                } else {
                    Log.d("Test", "Данные меню пустые")
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("restaurantId", restaurantId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}