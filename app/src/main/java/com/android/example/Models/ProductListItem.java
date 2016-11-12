package com.android.example.Models;

import java.util.ArrayList;

/**
 * Created by Lokesh on 11/10/2016.
 */

public class ProductListItem {
    String id;
    String title;
    String styleCode;
    String shortTitle;
    String description;
    double basePrice;
    double salePrice;
    String offer;
    ArrayList<String> sizeList;
    String priceInfo;
    String brandName;
    String brandId;
    String brandSlug;
    String storeId;
    String storeName;
    String storeLogo;
    String storeSlug;
    int logoHeight;
    int logoWidth;
    String logoUrl;
    String logoThumb;
    ArrayList<ProductImages> productImages;
    boolean addedToCart;
    String cartItemId;
    boolean buyable;
    boolean offerAvailable;
    boolean canDeliver;
    boolean canPickUp;
    String slug;
    ArrayList<ProductSizeList> productSizeList;
    double giftWrapAmount;
    ArrayList<String> tags;
    String inventoryId;
    boolean stokeAvailable;
    boolean isBookmarked;
    String itemType;

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStyleCode() {
        return styleCode;
    }

    public void setStyleCode(String styleCode) {
        this.styleCode = styleCode;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public ArrayList<String> getSizeList() {
        return sizeList;
    }

    public void setSizeList(ArrayList<String> sizeList) {
        this.sizeList = sizeList;
    }

    public String getPriceInfo() {
        return priceInfo;
    }

    public void setPriceInfo(String priceInfo) {
        this.priceInfo = priceInfo;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandSlug() {
        return brandSlug;
    }

    public void setBrandSlug(String brandSlug) {
        this.brandSlug = brandSlug;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }

    public String getStoreSlug() {
        return storeSlug;
    }

    public void setStoreSlug(String storeSlug) {
        this.storeSlug = storeSlug;
    }

    public int getLogoHeight() {
        return logoHeight;
    }

    public void setLogoHeight(int logoHeight) {
        this.logoHeight = logoHeight;
    }

    public int getLogoWidth() {
        return logoWidth;
    }

    public void setLogoWidth(int logoWidth) {
        this.logoWidth = logoWidth;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getLogoThumb() {
        return logoThumb;
    }

    public void setLogoThumb(String logoThumb) {
        this.logoThumb = logoThumb;
    }

    public ArrayList<ProductImages> getProductImages() {
        return productImages;
    }

    public void setProductImages(ArrayList<ProductImages> productImages) {
        this.productImages = productImages;
    }

    public boolean isAddedToCart() {
        return addedToCart;
    }

    public void setAddedToCart(boolean addedToCart) {
        this.addedToCart = addedToCart;
    }

    public String getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(String cartItemId) {
        this.cartItemId = cartItemId;
    }

    public boolean isBuyable() {
        return buyable;
    }

    public void setBuyable(boolean buyable) {
        this.buyable = buyable;
    }

    public boolean isOfferAvailable() {
        return offerAvailable;
    }

    public void setOfferAvailable(boolean offerAvailable) {
        this.offerAvailable = offerAvailable;
    }

    public boolean isCanDeliver() {
        return canDeliver;
    }

    public void setCanDeliver(boolean canDeliver) {
        this.canDeliver = canDeliver;
    }

    public boolean isCanPickUp() {
        return canPickUp;
    }

    public void setCanPickUp(boolean canPickUp) {
        this.canPickUp = canPickUp;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public ArrayList<ProductSizeList> getProductSizeList() {
        return productSizeList;
    }

    public void setProductSizeList(ArrayList<ProductSizeList> productSizeList) {
        this.productSizeList = productSizeList;
    }

    public double getGiftWrapAmount() {
        return giftWrapAmount;
    }

    public void setGiftWrapAmount(double giftWrapAmount) {
        this.giftWrapAmount = giftWrapAmount;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public boolean isStokeAvailable() {
        return stokeAvailable;
    }

    public void setStokeAvailable(boolean stokeAvailable) {
        this.stokeAvailable = stokeAvailable;
    }
}
