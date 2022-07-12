package com.example.lib;

import com.fbs.app.generated.ItemDTO;
import com.fbs.app.generated.MainmenuDTO;
import com.fbs.app.generated.Menu;
import com.google.flatbuffers.FlatBufferBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.List;

public class MyClass {
    public static void main(String[] args) {
//        String dir = "D:\\workspace\\LocationApplication\\mmkv\\src\\main\\java\\com\\example\\mmkv\\mmkv_2";
        try {
            FileInputStream fileInputStream = new FileInputStream("export.json");
            JsonReader reader = new JsonReader(new InputStreamReader(fileInputStream, "UTF-8"));
            Type listUserType = new TypeToken<Bean>() {
            }.getType();

            Bean bean = new Gson().fromJson(reader, listUserType);
            createFlatXmlFile(bean);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void createFlatXmlFile(Bean bean) {
        FlatBufferBuilder fb = new FlatBufferBuilder(100);
        int index = 0;
        List<Bean.MenuDTO.MainmenuDTO.ItemDTO> itemList = bean.menu.mainmenu.item;
        bean.menu.mainmenu.item.addAll(itemList);
        bean.menu.mainmenu.item.addAll(itemList);
        bean.menu.mainmenu.item.addAll(itemList);
        bean.menu.mainmenu.item.addAll(itemList);
        bean.menu.mainmenu.item.addAll(itemList);
        bean.menu.mainmenu.item.addAll(itemList);
        int[] dataArray = new int[bean.menu.mainmenu.item.size()];
        for (Bean.MenuDTO.MainmenuDTO.ItemDTO itemDTO : bean.menu.mainmenu.item) {
            int itemOffset = ItemDTO.createItemDTO(fb,
                    fb.createString(itemDTO.code),
                    fb.createString(itemDTO.activity),
                    fb.createString(itemDTO.iconum),
                    fb.createString(itemDTO.icon),
                    fb.createString(itemDTO.type),
                    fb.createString(itemDTO.extended),
                    fb.createString(itemDTO.parentid),
                    fb.createString(itemDTO.localUrl),
                    fb.createString(String.valueOf(itemDTO.authorityHide)),
                    fb.createString("" + itemDTO.moreentry),
                    fb.createString(itemDTO.id),
                    fb.createString(itemDTO.iconUrl),
                    fb.createString(itemDTO.homeAuth),
                    fb.createString(itemDTO.classX),
                    fb.createString(itemDTO.msgtype),
                    fb.createString(itemDTO.order + ""),
                    fb.createString(itemDTO.appnum + ""),
                    fb.createString(itemDTO.packageX),
                    fb.createString(itemDTO.textSize + ""),
                    fb.createString(itemDTO.business),
                    fb.createString(itemDTO.parentflag + ""),
                    fb.createString(itemDTO.textPostion),
                    fb.createString(itemDTO.navigateNote),
                    fb.createString(itemDTO.constructor),
                    fb.createString(itemDTO.relationId),
                    fb.createString(itemDTO.textColor),
                    fb.createString(itemDTO.url),
                    fb.createString(itemDTO.authority),
                    fb.createString(itemDTO.name),
                    fb.createString(itemDTO.textBackground),
                    fb.createString(itemDTO.hascontent + ""),
                    fb.createString(itemDTO.defaultLoad + ""),
                    fb.createString(itemDTO.menuAuthority),
                    fb.createString("ShowTitleLine"),
                    fb.createString("turnViewType")
            );
            dataArray[index] = itemOffset;
            index++;
        }


        int itemVector = MainmenuDTO.createItemVector(fb, dataArray);
        int mainmenuDTO = MainmenuDTO.createMainmenuDTO(fb, itemVector);

        int menuOffset = Menu.createMenu(fb,
                fb.createString(bean.menu.type),
                mainmenuDTO
        );
        fb.finish(menuOffset);

        ByteBuffer byteBuffer = fb.dataBuffer();
        outPutTofile(byteBuffer, "xml_flatbuffer");

        try {
            File file = new File("xml_flatbuffer");
            RandomAccessFile f = new RandomAccessFile(file, "r");
            byte[] data = new byte[(int) f.length()];
            f.readFully(data);
            ByteBuffer bb = ByteBuffer.wrap(data);
            Menu rootAsMenu1 = Menu.getRootAsMenu(bb);
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void outPutTofile(ByteBuffer byteBuffer, String filename) {

        ByteBuffer buf = byteBuffer;
        byte[] array = new byte[buf.remaining()];
        buf.get(array);

        try {
            String filePath = filename;
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(array);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}