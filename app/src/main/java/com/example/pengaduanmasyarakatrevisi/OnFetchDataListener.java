package com.example.pengaduanmasyarakatrevisi;

import com.example.pengaduanmasyarakatrevisi.Models.NewsHeadline;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponse> {
    void onfectchData(List<NewsHeadline> list, String message);
    void onError(String message);
}
