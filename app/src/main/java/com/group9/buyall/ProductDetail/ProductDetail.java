package com.group9.buyall.ProductDetail;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group9.buyall.R;

import java.util.ArrayList;
import java.util.List;

public class ProductDetail extends AppCompatActivity {

    private RecyclerView rcvPRODUCTDETAIL;
    private List<ProductDetailComment> MyListProductDetailComment;
    private List<Product_Detail> MyProduct_Detail,ProductDetailStack;
    private List<Product_Description> MyProduct_Description,ProductDescriptionStack;
    private ProductDetailAdapter productDetailAdapter;
    private ImageButton ibArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);
        MyProduct_Detail = new ArrayList<>();
        MyProduct_Description = new ArrayList<>();
        ProductDetailStack = new ArrayList<>();
        ProductDescriptionStack = new ArrayList<>();
        MyListProductDetailComment = new ArrayList<>();

        Product_Detail product_detail1 = new Product_Detail("1","Ram Laptop 8GB DDR4 3200MHz", 400000, "Nhanh","TPHCM",(float) 4.5,R.drawable.ddr4);
        Product_Detail product_detail2 = new Product_Detail("2","Ram Laptop 8GB DDR5 4800MHz", 560000, "Hỏa Tốc","TPHCM",(float) 4,R.drawable.ddr5);
        Product_Detail product_detail3 = new Product_Detail("3","Ram Laptop 8GB DDR3 1600MHz", 150000, "Hỏa Tốc","Đà Nẵng",(float) 3,R.drawable.ddr3);

        Product_Description product_description1 = new Product_Description("1","RAM DDR4 16GB 3200MHz là sự lựa chọn hoàn hảo cho những người dùng đòi hỏi hiệu suất cao trong các tác vụ nặng như gaming, chỉnh sửa video, và thiết kế đồ họa. Với tốc độ truyền tải dữ liệu nhanh hơn 50% so với DDR3, RAM DDR4 giúp hệ thống của bạn hoạt động mượt mà và tiết kiệm năng lượng hơn. Nâng cấp lên RAM DDR4 giúp bạn tối ưu hóa hiệu suất và trải nghiệm người dùng.");
        Product_Description product_description2 = new Product_Description("2", "RAM DDR5 32GB 4800MHz đem lại hiệu suất vượt trội cho các hệ thống máy tính thế hệ mới. Với băng thông cao hơn và khả năng xử lý nhiều dữ liệu hơn, DDR5 giúp tăng tốc độ load game và cải thiện thời gian phản hồi trong các ứng dụng đòi hỏi tài nguyên lớn. Đây là sự lựa chọn tối ưu cho các game thủ chuyên nghiệp và các nhà phát triển nội dung, đảm bảo mọi tác vụ diễn ra một cách mượt mà và hiệu quả.");
        Product_Description product_description3 = new Product_Description("3","RAM DDR3 8GB 1600MHz mang lại hiệu suất ổn định và nhanh chóng cho các hệ thống máy tính văn phòng và chơi game cơ bản. Với băng thông cao, RAM DDR3 hỗ trợ các tác vụ đa nhiệm mượt mà, giúp bạn dễ dàng chuyển đổi giữa các ứng dụng mà không gặp phải tình trạng giật lag. Đây là lựa chọn lý tưởng cho những ai muốn nâng cấp hiệu suất mà không tốn quá nhiều chi phí.");

        ProductDetailComment productDetailComment1 = new ProductDetailComment("1","1","1" ,"User 1", R.drawable.user,"Sản phẩm rất tuyệt vời, không có gì để chê",R.drawable.ddr4_1,R.drawable.ddr4_2,5);
        ProductDetailComment productDetailComment2 = new ProductDetailComment("1","2","2" ,"User 2", R.drawable.user,"Sản phẩm khá tốt, tối khá hài lòng",R.drawable.ddr4_2,0,4);
        ProductDetailComment productDetailComment3 = new ProductDetailComment("2","3","3" ,"User 3", R.drawable.user,"Sản phẩm cũng tạm ổn, shop giao hàng chậm",R.drawable.ddr5_1,R.drawable.ddr5_2,3);
        ProductDetailComment productDetailComment4 = new ProductDetailComment("2","4","4" ,"User 4", R.drawable.user,"Sản phẩm rất mạnh mẽ tôi rất thích, 2 thanh thừa sức chiến Wukong ",R.drawable.ddr5_3,R.drawable.ddr5_4,5);
        ProductDetailComment productDetailComment5 = new ProductDetailComment("3","3","5" ,"User 5", R.drawable.user,"Sản phẩm cũng hoạt động ổn định, giao hàng sớm hơn dự kiến",R.drawable.ddr3_1,R.drawable.ddr3_2,5);
        ProductDetailComment productDetailComment6 = new ProductDetailComment("3","4","6" ,"User 6", R.drawable.user,"Sản phẩm hoat động không ổn định lỗi màn hình xanh",R.drawable.ddr3_3,0,1);

        ProductDetailStack.add(product_detail1);
        ProductDetailStack.add(product_detail2);
        ProductDetailStack.add(product_detail3);

        ProductDescriptionStack.add(product_description1);
        ProductDescriptionStack.add(product_description2);
        ProductDescriptionStack.add(product_description3);

        ProductDetailComment.commentsList.clear();
        ProductDetailComment.commentsList.add(productDetailComment1);
        ProductDetailComment.commentsList.add(productDetailComment2);
        ProductDetailComment.commentsList.add(productDetailComment3);
        ProductDetailComment.commentsList.add(productDetailComment4);
        ProductDetailComment.commentsList.add(productDetailComment5);
        ProductDetailComment.commentsList.add(productDetailComment6);

        String productId = getIntent().getStringExtra("PRODUCT_ID");

        rcvPRODUCTDETAIL = findViewById(R.id.rcvPRODUCTDETAIL);
        productDetailAdapter = new ProductDetailAdapter(MyProduct_Detail, MyListProductDetailComment, MyProduct_Description, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvPRODUCTDETAIL.setLayoutManager(linearLayoutManager);
        rcvPRODUCTDETAIL.setAdapter(productDetailAdapter);

        ibArrow = findViewById(R.id.arrow);
        ibArrow.setOnClickListener(v -> {

            finish();
        });

        for (Product_Detail product_detail : ProductDetailStack){
            if (product_detail.getProductId().equals(productId)){
                MyProduct_Detail.add(product_detail);
                break;
            }
        }
        for (ProductDetailComment productDetailComment : ProductDetailComment.commentsList) {
            if (productDetailComment.getProductId().equals(productId)) {
                MyListProductDetailComment.add(productDetailComment);
            }
        }
        for (Product_Description product_description : ProductDescriptionStack) {
            if (product_description.getProductId().equals(productId)) {
                MyProduct_Description.add(product_description);
                break;
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}