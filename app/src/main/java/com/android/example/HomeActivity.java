package com.android.example;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.example.Adapters.ProductListAdapter;
import com.android.example.Constants.Constants;
import com.android.example.Constants.UrlConstants;
import com.android.example.Models.ProductListItem;
import com.android.example.Models.ProductSizeList;
import com.android.example.Models.ProductImages;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements UrlConstants, ProductListAdapter.OnButtonClickListener, Constants {

    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView mRecyclerView;
    private ProductListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ProductListItem> mProductDataList;
    private ArrayList<ProductListItem> mFoodDataList;
    private ArrayList<ProductListItem> mClothsDataList;
    private final int FOOD = 1, CLOTHS = 2, COMBINE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mProductDataList = new ArrayList<>();
        mAdapter = new ProductListAdapter(HomeActivity.this, mProductDataList);
        mAdapter.setButtonClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        mFoodDataList = new ArrayList<>();
        mClothsDataList = new ArrayList<>();
        apiCall();
    }

    private synchronized void apiCall() {
        sendProductRequestGet(BASE_URL + FOOD_URL, FOOD);
        sendProductRequestGet(BASE_URL + CLOTH_URL, CLOTHS);
        sendProductRequestGet("", COMBINE);
    }

    private void sendProductRequestGet(final String url, final int requestType) {
        if (isNetworkAvailable(HomeActivity.this)) {
            new ProductRequestGet(requestType).execute(url);
        } else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_home), "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            sendProductRequestGet(url, requestType);
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    @Override
    public void onButtonClick(int btnType, final int position) {
        switch (btnType) {
            case 1://bookmark
                if (mProductDataList.get(position).isBookmarked()) {
                    new MaterialDialog.Builder(this)
                            .title("Remove Bookmark")
                            .content("Are you confirm to remove name " + mProductDataList.get(position).getTitle() + " from Bookmark list.")
                            .positiveText("Yes")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    mProductDataList.get(position).setBookmarked(false);
                                    mAdapter.onDataSetChanged(mProductDataList);
                                }
                            })
                            .negativeText("No")
                            .show();
                } else {
                    new MaterialDialog.Builder(this)
                            .title("Bookmark")
                            .content("Are you confirm you want to add " + mProductDataList.get(position).getTitle() + " to Bookmark list.")
                            .positiveText("Yes")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    mProductDataList.get(position).setBookmarked(true);
                                    mAdapter.onDataSetChanged(mProductDataList);
                                }
                            })
                            .negativeText("No")
                            .show();
                }
                break;
            case 3://tag
                new MaterialDialog.Builder(this)
                        .title("TAG")
                        .items(mProductDataList.get(position).getTags())
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int position, CharSequence text) {
                                Snackbar.make(findViewById(R.id.activity_home), text, Snackbar.LENGTH_LONG).show();
                            }
                        })
                        .show();
                break;
            case 2://size
                new MaterialDialog.Builder(this)
                        .title("TAG")
                        .items(mProductDataList.get(position).getSizeList())
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int position, CharSequence text) {
                                Snackbar.make(findViewById(R.id.activity_home), text, Snackbar.LENGTH_LONG).show();
                            }
                        })
                        .show();
                break;
            case 4://item click
                Bundle bundle = new Bundle();
                bundle.putString(TITLE, mProductDataList.get(position).getTitle());
                bundle.putString(DESCRIPTION, mProductDataList.get(position).getDescription());
                startActivity(new Intent(HomeActivity.this, ProductDetailActivity.class).putExtras(bundle));
                break;
        }
    }

    private void updateProductList() {
        Handler mainHandler = new Handler(HomeActivity.this.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                int size = -1;
                if (mFoodDataList != null && mClothsDataList != null)
                    size = (mFoodDataList.size() >= mClothsDataList.size()) ? mFoodDataList.size() : mClothsDataList.size();
                else
                    size = 0;
                if (size > 0) {
                    for (int i = 0; i < size; i++) {
                        if (mFoodDataList.size() > i) {
                            mProductDataList.add(mFoodDataList.get(i));
                        }
                        if (mClothsDataList.size() > i) {
                            mProductDataList.add(mClothsDataList.get(i));
                        }
                    }
                }
                mAdapter.onDataSetChanged(mProductDataList);
            }
        };
        mainHandler.post(myRunnable);
    }

    private class ProductRequestGet extends AsyncTask<String, Void, String> {

        int requestType = 0;

        ProductRequestGet(int requestType) {
            this.requestType = requestType;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            switch (requestType) {
                case FOOD:
                    if (s != null)
                        mFoodDataList = parseProductResponse(s, FOOD);
                    break;
                case CLOTHS:
                    if (s != null)
                        mClothsDataList = parseProductResponse(s, CLOTHS);
                    break;
                case COMBINE:
                    updateProductList();
                    break;
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String _response = null;
            if (requestType == FOOD | requestType == CLOTHS) {
                HttpClient httpClient = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 30000);
                HttpGet httpGet = new HttpGet(params[0]);
                try {
                    HttpResponse response = httpClient.execute(httpGet);
                    _response = EntityUtils.toString(response.getEntity());
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return _response;
        }
    }

    private ArrayList<ProductListItem> parseProductResponse(String result, int requestType) {
        ArrayList<ProductListItem> mProductDataList = new ArrayList<>();
        try {
            JSONObject response = new JSONObject(result);
            JSONArray productArray = response.getJSONArray("products");
            if (productArray != null && productArray.length() > 0) {
                for (int i = 0; i < productArray.length(); i++) {
                    JSONObject productObj = productArray.getJSONObject(i);
                    ProductListItem rowData = new ProductListItem();
                    rowData.setId(productObj.getString("id"));
                    rowData.setTitle(productObj.getString("title"));
                    rowData.setStyleCode(productObj.getString("style_code"));
                    rowData.setShortTitle(productObj.getString("short_title"));
                    rowData.setDescription(productObj.getString("description"));
                    rowData.setBasePrice(productObj.getDouble("base_price"));
                    rowData.setSalePrice(productObj.getDouble("sale_price"));
                    rowData.setOffer(productObj.getString("offer"));
                    JSONArray sizeList = productObj.getJSONArray("size_list");
                    ArrayList<String> mSizeList = new ArrayList<>();
                    if (sizeList != null && sizeList.length() > 0)
                        for (int j = 0; j < sizeList.length(); j++) {
                            String size = sizeList.getString(j);
                            mSizeList.add(size);
                        }
                    rowData.setSizeList(mSizeList);
                    rowData.setPriceInfo(productObj.getString("price_info"));
                    rowData.setBrandName(productObj.getString("brand_name"));
                    rowData.setBrandId(productObj.getString("brand_id"));
                    rowData.setBrandSlug(productObj.getString("brand_slug"));
                    rowData.setStoreId(productObj.getString("store_id"));
                    rowData.setStoreName(productObj.getString("store_name"));
                    rowData.setStoreLogo(productObj.getString("store_logo"));
                    rowData.setStoreSlug(productObj.getString("store_slug"));
                    JSONObject logo = productObj.getJSONObject("logo");
                    rowData.setLogoHeight(logo.getInt("height"));
                    rowData.setLogoWidth(logo.getInt("width"));
                    rowData.setLogoUrl(logo.getString("url"));
                    rowData.setLogoThumb(productObj.getString("logo_thumb"));
                    JSONArray productImagesArray = productObj.getJSONArray("images");
                    ArrayList<ProductImages> mProductImages = new ArrayList<>();
                    if (productImagesArray != null && productImagesArray.length() > 0)
                        for (int k = 0; k < productImagesArray.length(); k++) {
                            JSONObject imagesObj = productImagesArray.getJSONObject(k);
                            ProductImages productImages = new ProductImages();
                            productImages.setHeight(imagesObj.getInt("height"));
                            productImages.setWidth(imagesObj.getInt("width"));
                            productImages.setUrl(imagesObj.getString("url"));
                            mProductImages.add(productImages);
                        }
                    rowData.setProductImages(mProductImages);
                    rowData.setAddedToCart(productObj.getBoolean("added_to_wishcart"));
                    rowData.setCartItemId(productObj.getString("wishcart_item_id"));
                    rowData.setBuyable(productObj.getBoolean("buyable"));
                    rowData.setOfferAvailable(productObj.getBoolean("offer_available"));
                    rowData.setCanDeliver(productObj.getBoolean("can_deliver"));
                    rowData.setCanPickUp(productObj.getBoolean("can_pickup"));
                    rowData.setSlug(productObj.getString("slug"));
                    JSONObject productType = productObj.getJSONObject("product_type");
                    ArrayList<ProductSizeList> mProductSizeList = new ArrayList<>();
                    JSONArray productSizesArray = null;
                    if (productType.has("sizes"))
                        productSizesArray = productType.getJSONArray("sizes");
                    if (productSizesArray != null && productSizesArray.length() > 0)
                        for (int j = 0; j < productSizesArray.length(); j++) {
                            JSONObject sizesObj = productSizesArray.getJSONObject(j);
                            ProductSizeList sizeRowData = new ProductSizeList();
                            sizeRowData.setColor(sizesObj.getString("color"));
                            sizeRowData.setQuantity(sizesObj.getInt("quantity"));
                            sizeRowData.setAvailable(sizesObj.getBoolean("available"));
                            sizeRowData.setMaterial(sizesObj.getString("material"));
                            sizeRowData.setSize(sizesObj.getString("size"));
                            mProductSizeList.add(sizeRowData);
                        }
                    rowData.setProductSizeList(mProductSizeList);
                    rowData.setGiftWrapAmount(productObj.getDouble("gift_wrap_amount"));
                    JSONArray tagsArray = productObj.getJSONArray("tags");
                    ArrayList<String> tagsList = new ArrayList<>();
                    if (tagsArray != null && tagsArray.length() > 0)
                        for (int j = 0; j < tagsArray.length(); j++) {
                            String tag = tagsArray.getString(j);
                            tagsList.add(tag);
                        }
                    rowData.setTags(tagsList);
                    rowData.setInventoryId(productObj.getString("inventory_id"));
                    rowData.setStokeAvailable(productObj.getBoolean("stock_available"));
                    switch (requestType) {
                        case FOOD:
                            rowData.setItemType("F");
                            break;
                        case CLOTHS:
                            rowData.setItemType("C");
                            break;
                    }
                    mProductDataList.add(rowData);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "List Sze : " + mProductDataList.size());
        return mProductDataList;
    }

    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
