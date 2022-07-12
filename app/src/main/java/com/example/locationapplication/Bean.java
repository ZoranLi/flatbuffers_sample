package com.example.locationapplication;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Bean implements Serializable {

    @SerializedName("menu")
    public MenuDTO menu;

    public static class MenuDTO {
        @SerializedName("mainmenu")
        public MainmenuDTO mainmenu;
        @SerializedName("type")
        public String type;

        public static class MainmenuDTO {
            @SerializedName("item")
            public List<ItemDTO> item;

            public static class ItemDTO {
                @SerializedName("code")
                public String code;
                @SerializedName("activity")
                public String activity;
                @SerializedName("iconum")
                public String iconum;
                @SerializedName("icon")
                public String icon;
                @SerializedName("type")
                public String type;
                @SerializedName("extended")
                public String extended;
                @SerializedName("parentid")
                public String parentid;
                @SerializedName("localUrl")
                public String localUrl;
                @SerializedName("authorityHide")
                public Integer authorityHide;
                @SerializedName("moreentry")
                public Integer moreentry;
                @SerializedName("id")
                public String id;
                @SerializedName("iconUrl")
                public String iconUrl;
                @SerializedName("homeAuth")
                public String homeAuth;
                @SerializedName("class")
                public String classX;
                @SerializedName("msgtype")
                public String msgtype;
                @SerializedName("order")
                public Integer order;
                @SerializedName("appnum")
                public Integer appnum;
                @SerializedName("package")
                public String packageX;
                @SerializedName("textSize")
                public Integer textSize;
                @SerializedName("business")
                public String business;
                @SerializedName("parentflag")
                public Integer parentflag;
                @SerializedName("textPostion")
                public String textPostion;
                @SerializedName("navigateNote")
                public String navigateNote;
                @SerializedName("constructor")
                public String constructor;
                @SerializedName("relationId")
                public String relationId;
                @SerializedName("textColor")
                public String textColor;
                @SerializedName("url")
                public String url;
                @SerializedName("authority")
                public String authority;
                @SerializedName("name")
                public String name;
                @SerializedName("textBackground")
                public String textBackground;
                @SerializedName("hascontent")
                public Integer hascontent;
                @SerializedName("DefaultLoad")
                public Integer defaultLoad;
                @SerializedName("menuAuthority")
                public String menuAuthority;
                @SerializedName("ShowTitleLine")
                public List<Integer> showTitleLine;
                @SerializedName("turnViewType")
                public List<Integer> turnViewType;
            }
        }
    }
}
