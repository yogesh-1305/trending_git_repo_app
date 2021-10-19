package com.example.trendinggitrepos.ui.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.trendinggitrepos.databinding.FragmentWebViewBinding
import com.example.trendinggitrepos.util.UtilityMethods.gone
import com.example.trendinggitrepos.util.UtilityMethods.hasNetwork
import com.example.trendinggitrepos.util.UtilityMethods.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : Fragment() {

    private lateinit var binding: FragmentWebViewBinding

    private val args: WebViewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkForNetworkAndLoadWebPage()
        setClickListeners()
    }

    private fun setClickListeners() {

        binding.retryButton.setOnClickListener {
            checkForNetworkAndLoadWebPage()
        }

        binding.webView.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN) {
                when (keyCode) {
                    KeyEvent.KEYCODE_BACK -> {
                        if (binding.webView.canGoBack()){
                            binding.webView.goBack()
                            return@setOnKeyListener true
                        }
                    }
                }
            }
            return@setOnKeyListener false
        }
    }

    private fun checkForNetworkAndLoadWebPage() {
        if (hasNetwork(requireContext())!!) {
            showWebViewLayout()
            setupWebView()
        } else {
            showNoInternetAnimation()
        }
    }

    private fun showWebViewLayout() {
        binding.noInternetLottieAnim.gone()
        binding.retryButton.gone()
        binding.webView.show()
    }

    private fun showNoInternetAnimation() {
       binding.noInternetLottieAnim.show()
        binding.retryButton.show()
        binding.webView.gone()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.webView.apply {
            settings.domStorageEnabled = true
            settings.javaScriptEnabled = true

            webChromeClient = object : WebChromeClient() {
                override fun onPermissionRequest(request: PermissionRequest?) {
                    request?.grant(request.resources)
                }

                @RequiresApi(Build.VERSION_CODES.N)
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    binding.progressBar.apply {
                        show()
                        setProgress(newProgress, true)
                    }
                }
            }
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    binding.progressBar.show()
                    view?.loadUrl(request?.url.toString())
                    binding.webView.scrollTo(0,0)
                    return true
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    binding.progressBar.show()
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.progressBar.gone()
                }
            }
            loadUrl(args.customRepository.url.toString())
        }
    }
}