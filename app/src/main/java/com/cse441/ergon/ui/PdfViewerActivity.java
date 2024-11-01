package com.cse441.ergon.ui;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cse441.ergon.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.InputStream;

public class PdfViewerActivity extends AppCompatActivity {

    private PDFView pdfView;
    private String pdfUrl;
    private ImageButton backpdf;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        // Ánh xạ các view
        backpdf = findViewById(R.id.backpdf);

        pdfView = findViewById(R.id.pdfView);

        // Lấy dữ liệu từ Intent
        pdfUrl = getIntent().getStringExtra("pdfUrl");


        // Nút quay lại
        backpdf.setOnClickListener(v -> finish());

        // Tải và hiển thị PDF nếu URL không null
        if (pdfUrl != null) {
            downloadAndDisplayPdf(pdfUrl);
        } else {
            Toast.makeText(this, "Không tìm thấy URL của tài liệu", Toast.LENGTH_SHORT).show();
        }
    }

    private void downloadAndDisplayPdf(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(PdfViewerActivity.this, "Lỗi khi tải tài liệu", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Lưu file PDF vào bộ nhớ tạm
                    File pdfFile = new File(getCacheDir(), "downloaded_pdf.pdf");
                    try (InputStream inputStream = response.body().byteStream();
                         FileOutputStream outputStream = new FileOutputStream(pdfFile)) {
                        byte[] buffer = new byte[2048];
                        int length;
                        while ((length = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, length);
                        }
                    }

                    // Hiển thị file PDF bằng PDFView
                    runOnUiThread(() -> displayPdf(pdfFile));
                } else {
                    runOnUiThread(() -> Toast.makeText(PdfViewerActivity.this, "Không thể tải tài liệu", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void displayPdf(File pdfFile) {
        pdfView.fromUri(Uri.fromFile(pdfFile))
                .defaultPage(0)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .enableAnnotationRendering(true)
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        Toast.makeText(PdfViewerActivity.this, "Tài liệu đã được tải xong", Toast.LENGTH_SHORT).show();
                    }
                })
                .onPageError(new OnPageErrorListener() {
                    @Override
                    public void onPageError(int page, Throwable t) {
                        Toast.makeText(PdfViewerActivity.this, "Lỗi khi hiển thị trang " + page, Toast.LENGTH_SHORT).show();
                    }
                })
                .spacing(10)
                .pageFitPolicy(FitPolicy.BOTH)
                .load();
    }
}
