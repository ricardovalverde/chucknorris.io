package com.example.chucknorrisio.presentation;

import com.example.chucknorrisio.Colors;
import com.example.chucknorrisio.MainActivity;
import com.example.chucknorrisio.datasource.CategoryRemoteDataSource;
import com.example.chucknorrisio.model.CategoryItem;

import java.util.ArrayList;
import java.util.List;

public class CategoryPresenter implements CategoryRemoteDataSource.ListCategoriesCallback {

    private final MainActivity view;
    private final CategoryRemoteDataSource dataSource;


    public CategoryPresenter(MainActivity view, CategoryRemoteDataSource dataSource) {
        this.view = view;
        this.dataSource = dataSource;
    }

    public void requestAll() {
        this.view.showProgressBar();
        this.dataSource.findAll(this);
    }

    @Override
    public void onSuccess(List<String> response) {
        List<CategoryItem> categoryItems = new ArrayList<>();
        for (String val : response) {
            categoryItems.add(new CategoryItem(val, Colors.RandomColor()));
        }
        view.showCategories(categoryItems);
    }

    @Override
    public void onError(String message) {
        this.view.showFailure(message);
    }

    @Override
    public void onComplete() {
        this.view.hideProgressBar();

    }

}
