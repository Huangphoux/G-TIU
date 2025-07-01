package com.example.g_tiu.helper;

import com.example.g_tiu.R;
import com.example.g_tiu.item.Color;
import com.example.g_tiu.item.IconModel;

import java.util.ArrayList;
import java.util.List;

public class AppConstants {

    public static List<IconModel> getIcons() {
        ArrayList<IconModel> icons = new ArrayList<>();

        icons.add(new IconModel(icons.size(), R.drawable.icon_category_allowance));
        icons.add(new IconModel(icons.size(), R.drawable.icon_category_beauty));
        icons.add(new IconModel(icons.size(), R.drawable.icon_category_bills));
        icons.add(new IconModel(icons.size(), R.drawable.icon_category_business));
        icons.add(new IconModel(icons.size(), R.drawable.icon_category_entertainment));
        icons.add(new IconModel(icons.size(), R.drawable.icon_category_family));
        icons.add(new IconModel(icons.size(), R.drawable.icon_category_food));
        icons.add(new IconModel(icons.size(), R.drawable.icon_category_grocery));
        icons.add(new IconModel(icons.size(), R.drawable.icon_category_health));
        icons.add(new IconModel(icons.size(), R.drawable.icon_category_home));
        icons.add(new IconModel(icons.size(), R.drawable.icon_category_interest));
        icons.add(new IconModel(icons.size(), R.drawable.icon_category_investment));
        icons.add(new IconModel(icons.size(), R.drawable.icon_category_salary));
        icons.add(new IconModel(icons.size(), R.drawable.icon_category_shopping));
        icons.add(new IconModel(icons.size(), R.drawable.icon_category_study));
        icons.add(new IconModel(icons.size(), R.drawable.icon_category_transport));

        return icons;
    }

    public static List<Color> getColors() {
        List<Color> colorList = new ArrayList<>();
        colorList.add(new Color(0, "#FFFFFF"));  // trắng
        colorList.add(new Color(1, "#FF5733"));  // đỏ cam
        colorList.add(new Color(2, "#33FF57"));  // xanh lá tươi
        colorList.add(new Color(3, "#3357FF"));  // xanh dương
        colorList.add(new Color(4, "#FF33A6"));  // hồng đậm
        colorList.add(new Color(5, "#33FFF6"));  // xanh ngọc
        colorList.add(new Color(6, "#F6FF33"));  // vàng sáng
        colorList.add(new Color(7, "#8D33FF"));  // tím
        colorList.add(new Color(8, "#FF8D33"));  // cam sáng
        colorList.add(new Color(9, "#33FF8D"));  // xanh bạc hà
        colorList.add(new Color(10, "#338DFF")); // xanh dương nhạt
        colorList.add(new Color(11, "#FF3333")); // đỏ
        colorList.add(new Color(12, "#33FF33")); // xanh lá
        colorList.add(new Color(13, "#3333FF")); // xanh biển đậm
        colorList.add(new Color(14, "#FF33FF")); // hồng tím
        colorList.add(new Color(15, "#33FFFF")); // xanh nước biển
        colorList.add(new Color(16, "#FFFF33")); // vàng
        colorList.add(new Color(17, "#800000")); // đỏ rượu vang
        colorList.add(new Color(18, "#008080")); // teal
        colorList.add(new Color(19, "#808000")); // vàng olive
        colorList.add(new Color(20, "#000000")); // đen
        return colorList;
    }
}
