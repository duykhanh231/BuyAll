package com.group9.buyall.ProductDetail;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group9.buyall.CartFragment;
import com.group9.buyall.ProductList.Product_List;
import com.group9.buyall.R;

import java.util.ArrayList;
import java.util.List;

public class ProductDetail extends AppCompatActivity {
    private RecyclerView rcvPRODUCTDETAIL;
    private List<Product_All_Image> MyProduct_All_Image;
    private List<Linked_Product> MyLinked_Product;
    private List<ProductDetailComment> MyListProductDetailComment;
    private List<Product_List> MyProduct_List;
    private List<Product_Description> MyProduct_Description;
    private ProductDetailAdapter productDetailAdapter;
    private ImageButton ibArrow,ibCart;
    private String ss = "R1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);
        MyProduct_All_Image = new ArrayList<>();
        MyLinked_Product = new ArrayList<>();
        MyProduct_List = new ArrayList<>();
        MyProduct_Description = new ArrayList<>();
        MyListProductDetailComment = new ArrayList<>();

        int productId = getIntent().getIntExtra("PRODUCT_ID", 0);

        rcvPRODUCTDETAIL = findViewById(R.id.rcvPRODUCTDETAIL);
        productDetailAdapter = new ProductDetailAdapter(MyProduct_All_Image,MyLinked_Product,MyProduct_List, MyListProductDetailComment, MyProduct_Description, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvPRODUCTDETAIL.setLayoutManager(linearLayoutManager);


        ibCart = findViewById(R.id.cart);
        View.OnClickListener cartClickListener = v -> showCartFragment();
        ibCart.setOnClickListener(cartClickListener);

        ibArrow = findViewById(R.id.arrow);

        ibArrow.setOnClickListener(v -> {
            FrameLayout cartFragmentContainer = findViewById(R.id.cart_fragment_container);
            FrameLayout fullCommentContainer = findViewById(R.id.fullcomment_container);

            if (cartFragmentContainer.getVisibility() == View.VISIBLE&&fullCommentContainer.getVisibility() == View.INVISIBLE) {
                cartFragmentContainer.setVisibility(View.GONE);
                fullCommentContainer.setVisibility(View.VISIBLE);
            } else if (cartFragmentContainer.getVisibility() == View.VISIBLE) {
                cartFragmentContainer.setVisibility(View.GONE);
            }else if (fullCommentContainer.getVisibility() == View.VISIBLE) {
                fullCommentContainer.setVisibility(View.GONE);
            } else {
                finish();
            }
        });


        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("ProductAllImage").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<Product_All_Image> productAllImages = new ArrayList<>();

                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        int productID = userSnapshot.child("ProductID").getValue(Integer.class);
                        boolean isMAIN = userSnapshot.child("IsMain").getValue(boolean.class);

                        if (productID != productId) continue;

                        int ImageID = userSnapshot.child("ImageID").getValue(Integer.class);
                        String ProductImageURL = userSnapshot.child("ProductImageURL").getValue(String.class);

                        Product_All_Image image = new Product_All_Image(ImageID, productID, ProductImageURL, isMAIN);

                        if (isMAIN) {
                            productAllImages.add(0, image);
                        } else {
                            productAllImages.add(image);
                        }
                    }

                    MyProduct_All_Image.addAll(productAllImages);
                } else {
                    Log.d("Firebase", "ko có ProductAllImage khả thi.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Lỗi truy xuất ProductAllImage", databaseError.toException());
            }
        });

        database.child("ProductList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<Product_List> productLists = new ArrayList<>();

                    for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                        int productID = productSnapshot.child("ProductID").getValue(int.class);
                        if (productID != productId) continue;
                        String productName = productSnapshot.child("ProductName").getValue(String.class);
                        String productGroup = productSnapshot.child("ProductGroup").getValue(String.class);
                        String productImageURL = productSnapshot.child("ProductImageURL").getValue(String.class);
                        String productType = productSnapshot.child("ProductType").getValue(String.class);
                        Double productPrice = productSnapshot.child("ProductPrice").getValue(Double.class);
                        Double productRating = productSnapshot.child("ProductRating").getValue(Double.class);
                        String productShippingMethod = productSnapshot.child("ProductShippingMethod").getValue(String.class);
                        int productStock = productSnapshot.child("Stock").getValue(int.class);
                        Product_List product = new Product_List(
                                productID, productType, productGroup, productName, productPrice, productRating, productShippingMethod, productImageURL,productStock);
                        productLists.add(product);
                        ss = productGroup;
                    }
                    MyProduct_List.addAll(productLists);
                } else {
                    Log.d("Firebase", "ko có ProductDescriptions khả thi.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Lỗi truy xuất ProductDescription", databaseError.toException());
            }
        });

        database.child("ProductLinked").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<Linked_Product> linkedProducts = new ArrayList<>();

                    for (DataSnapshot linkedproductSnapshot : dataSnapshot.getChildren()) {
                        String linkedID = linkedproductSnapshot.child("LinkedID").getValue(String.class);
                        if (!linkedID.equals(ss)) continue;

                        int productID = linkedproductSnapshot.child("ProductID").getValue(Integer.class);
                        double ProductPrice = linkedproductSnapshot.child("ProductPrice").getValue(Double.class);
                        String ProductFewInfo = linkedproductSnapshot.child("ProductFewInfo").getValue(String.class);

                        Linked_Product linkedProduct = new Linked_Product(linkedID,productID,ProductFewInfo, ProductPrice);
                        linkedProducts.add(linkedProduct);
                    }
                    MyLinked_Product.addAll(linkedProducts);
                } else {
                    Log.d("Firebase", "ko có ProductDescriptions khả thi.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Lỗi truy xuất ProductDescription", databaseError.toException());
            }
        });




        database.child("ProductComment").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<ProductDetailComment> productDetailCommentList = new ArrayList<>();
                    int k=0;
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        if (k>2){ break;}
                        int productID = userSnapshot.child("ProductID").getValue(Integer.class);
                        if (productID != productId) continue;
                        int commentID = userSnapshot.child("CommentID").getValue(Integer.class);
                        int userID = userSnapshot.child("UserID").getValue(Integer.class);
                        String userName = userSnapshot.child("UserName").getValue(String.class);
                        String userImageURL = userSnapshot.child("UserImageURL").getValue(String.class);
                        String userComment = userSnapshot.child("UserComment").getValue(String.class);
                        String productImage1 = userSnapshot.child("ProductImage1").getValue(String.class);
                        String productImage2 = userSnapshot.child("ProductImage2").getValue(String.class);
                        int userRating = userSnapshot.child("UserRating").getValue(Integer.class);

                        ProductDetailComment comment = new ProductDetailComment(
                                productID, commentID, userID, userName, userImageURL, userComment, productImage1, productImage2, userRating);
                        productDetailCommentList.add(comment);
                        k++;
                    }
                    MyListProductDetailComment.addAll(productDetailCommentList);
                } else {
                    Log.d("Firebase", "ko có ProductComments khả thi.");
                }

                database.child("ProductDescription").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            List<Product_Description> productDescriptions = new ArrayList<>();

                            for (DataSnapshot descriptionSnapshot : dataSnapshot.getChildren()) {
                                int productID = descriptionSnapshot.child("ProductID").getValue(Integer.class);
                                if (productID != productId) continue;

                                String description = descriptionSnapshot.child("ProductDescription").getValue(String.class);

                                Product_Description descriptionObj = new Product_Description(productID, description);
                                productDescriptions.add(descriptionObj);
                                break;
                            }

                            MyProduct_Description.addAll(productDescriptions);
                            rcvPRODUCTDETAIL.setAdapter(productDetailAdapter);
                        } else {
                            Log.d("Firebase", "ko có ProductDescriptions khả thi.");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("Firebase", "Lỗi truy xuất ProductDescription", databaseError.toException());
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Lỗi truy xuất ProductComment", databaseError.toException());
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void showCartFragment() {
        CartFragment cartFragment = new CartFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.cart_fragment_container, cartFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        FrameLayout fullCommentContainer = findViewById(R.id.fullcomment_container);
        if (fullCommentContainer.getVisibility() == View.VISIBLE) {
            fullCommentContainer.setVisibility(View.INVISIBLE);
        }
        // Hiển thị `cart_fragment_container`
        FrameLayout cartFragmentContainer = findViewById(R.id.cart_fragment_container);
        cartFragmentContainer.setVisibility(View.VISIBLE);
    }
}
