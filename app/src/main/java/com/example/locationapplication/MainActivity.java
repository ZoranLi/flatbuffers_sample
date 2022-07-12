package com.example.locationapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.fbs.app.generated.Animal;
import com.fbs.app.generated.ItemDTO;
import com.fbs.app.generated.MainmenuDTO;
import com.fbs.app.generated.Menu;
import com.fbs.app.generated.ObjectLevel;
import com.google.common.base.CaseFormat;
import com.google.flatbuffers.FlatBufferBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.protobuf.generated.Annotations;
import com.google.gson.protobuf.generated.Bag;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.protobuf.GeneratedMessageV3;
import com.jinher.protobuf.generated.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends FragmentActivity {

    ProtoTypeAdapter.Builder protoTypeAdapter = ProtoTypeAdapter.newBuilder()
            .setEnumSerialization(ProtoTypeAdapter.EnumSerialization.NAME)
            .addSerializedNameExtension(Annotations.serializedName)
            .addSerializedEnumValueExtension(Annotations.serializedValue);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView viewById = findViewById(R.id.tvHelloWorld);

        long start = System.currentTimeMillis();
        Log.e("akdjkdfjkd", "onCreate: " + start);
        try {
            InputStream open = getAssets().open("aa.xml");
            pase(open);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "时间：" + (System.currentTimeMillis() - start), Toast.LENGTH_SHORT).show();
        Log.e("akdjkdfjkd", "onCreate: " + (System.currentTimeMillis() - start));
        viewById.append((System.currentTimeMillis() - start) + "\n");
        start = System.currentTimeMillis();
        try {
            InputStream open = getAssets().open("export.json");
            JsonReader reader = new JsonReader(new InputStreamReader(open, "UTF-8"));
            Type listUserType = new TypeToken<Bean>() {
            }.getType();

            Bean bean = new Gson().fromJson(reader, listUserType);
//            createFlatXmlFile(bean);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "时间1111：" + (System.currentTimeMillis() - start), Toast.LENGTH_SHORT).show();
        Log.e("akdjkdfjkd", "onCreate: " + (System.currentTimeMillis() - start));
        viewById.append((System.currentTimeMillis() - start) + "\n");

//        testProtoWithAnnotations_deserialize();

//        testJinheSerialize();

//        testFlatBuffer();

        start = System.currentTimeMillis();
        readFlatBuffer();
        Toast.makeText(this, "FlatBuffer时间：" + (System.currentTimeMillis() - start), Toast.LENGTH_SHORT).show();
    }


    private void readFlatBuffer() {
        try {
            File file = new File(getFilesDir() + "/xml_flatbuffer");
            RandomAccessFile f = new RandomAccessFile(file, "r");
            byte[] data = new byte[(int) f.length()];
            f.readFully(data);
            ByteBuffer bb = ByteBuffer.wrap(data);
            Menu rootAsMenu1 = Menu.getRootAsMenu(bb);
            Log.e(getClass().getName(),rootAsMenu1.type()+rootAsMenu1.mainmenu().itemVector());
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFlatXmlFile(Bean bean) {
        FlatBufferBuilder fb = new FlatBufferBuilder(100);
        int index = 0;
        List<Bean.MenuDTO.MainmenuDTO.ItemDTO> itemList = bean.menu.mainmenu.item;
        bean.menu.mainmenu.item.addAll(itemList);
        bean.menu.mainmenu.item.addAll(itemList);
        bean.menu.mainmenu.item.addAll(itemList);
        int[] dataArray = new int[bean.menu.mainmenu.item.size()];
        for (com.example.locationapplication.Bean.MenuDTO.MainmenuDTO.ItemDTO itemDTO : bean.menu.mainmenu.item) {
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
        outPutTofile(byteBuffer, "/xml_flatbuffer");


        try {

            File file = new File(getFilesDir() + "/xml_flatbuffer");
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

    /**
     * 测试 flatBuffer
     */
    private void testFlatBuffer() {
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(100);
        int objectLevel = ObjectLevel.createObjectLevel(flatBufferBuilder,
                flatBufferBuilder.createString("level_name"),
                flatBufferBuilder.createString("id")
        );

        int[] dataArray = new int[1];
        dataArray[0] = objectLevel;

        int objVector = Animal.createObjVector(flatBufferBuilder, dataArray);

        int cowOffset = Animal.createAnimal(flatBufferBuilder,
                flatBufferBuilder.createString("name"),
                flatBufferBuilder.createString("sound"),
                (short) 720,
                objVector
        );

        flatBufferBuilder.finish(cowOffset);

        ByteBuffer byteBuffer = flatBufferBuilder.dataBuffer();

        Animal rootAsAnimal = Animal.getRootAsAnimal(byteBuffer);
        Log.e(getClass().getName(), rootAsAnimal.toString());


        outPutTofile(byteBuffer, "/flat_buffers");


    }

    /**
     * 将解析数据持久化到文本
     * @param byteBuffer
     * @param filename
     */
    private void outPutTofile(ByteBuffer byteBuffer, String filename) {
//        byte[] array = byteBuffer.array();

        ByteBuffer buf = byteBuffer;
        byte[] array = new byte[buf.remaining()];
        buf.get(array);

        try {
            String filePath = getFilesDir() + filename;
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
//            // append or overwrite the file
//            boolean append = false;
//            FileChannel channel = null;
//            channel = new FileOutputStream(file, append).getChannel();
//
//            // Flips this buffer.  The limit is set to the current position and then
//            // the position is set to zero.  If the mark is defined then it is discarded.
//            byteBuffer.flip();
//            // Writes a sequence of bytes to this channel from the given buffer.
//            channel.write(byteBuffer);
//            // close the channel
//            channel.close();

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(array);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    private void testJinheSerialize() {
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(GeneratedMessageV3.class, protoTypeAdapter.build())
                .create();
        try {
            InputStream open = getAssets().open("export.json");
            JsonReader reader = new JsonReader(new InputStreamReader(open, "UTF-8"));
            Type listUserType = new TypeToken<Xml.SomeMessage>() {
            }.getType();
            long start = System.currentTimeMillis();
            Xml.SomeMessage proto = gson.fromJson(reader, listUserType);
//            String rebuilt = gson.toJson(proto);
            Toast.makeText(this, "sdjk-->" + (System.currentTimeMillis() - start), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        Xml.SomeMessage.Builder builder = Xml.SomeMessage.newBuilder();
//        Xml.SomeMessage someMessage = builder.build();
    }

    public void testProtoWithAnnotations_deserialize() {
        String json = String.format("{  %n"
                + "   \"id\":\"41e5e7fd6065d101b97018a465ffff01\",%n"
                + "   \"expiration_date\":{  %n"
                + "      \"month\":\"12\",%n"
                + "      \"year\":\"2017\",%n"
                + "      \"timeStamp\":\"9864653135687\",%n"
                + "      \"countryCode5f55\":\"en_US\"%n"
                + "   },%n"
                // Don't define innerMessage1
                + "   \"innerMessage2\":{  %n"
                // Set a number as a string; it should work
                + "      \"nIdCt\":\"98798465\",%n"
                + "      \"content\":\"text/plain\",%n"
                + "      \"$binary_data$\":[  %n"
                + "         {  %n"
                + "            \"data\":\"OFIN8e9fhwoeh8((⁹8efywoih\",%n"
                // Don't define width
                + "            \"height\":665%n"
                + "         },%n"
                + "         {  %n"
                // Define as an int; it should work
                + "            \"data\":65,%n"
                + "            \"width\":-56684%n"
                // Don't define height
                + "         }%n"
                + "      ]%n"
                + "   },%n"
                // Define a bunch of non recognizable data
                + "   \"non_existing\":\"foobar\",%n"
                + "   \"stillNot\":{  %n"
                + "      \"bunch\":\"of_useless data\"%n"
                + "   }%n"
                + "}");
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(GeneratedMessageV3.class, protoTypeAdapter.build())
                .create();
        Bag.ProtoWithAnnotations proto = gson.fromJson(json, Bag.ProtoWithAnnotations.class);
        String rebuilt = gson.toJson(proto);
        Toast.makeText(this, "sdfjkdsjk", Toast.LENGTH_SHORT).show();
    }


    public void testProtoWithAnnotations_serialize() {
        GsonBuilder gsonWithLowerHyphen = new GsonBuilder()
                .registerTypeHierarchyAdapter(GeneratedMessageV3.class, protoTypeAdapter
                        .setFieldNameSerializationFormat(CaseFormat.LOWER_UNDERSCORE, CaseFormat.LOWER_HYPHEN)
                        .build());

        Bag.ProtoWithAnnotations proto = Bag.ProtoWithAnnotations.newBuilder()
                .setId("09f3j20839h032y0329hf30932h0nffn")
                .setOuterMessage(Bag.OuterMessage.newBuilder()
                        .setMonth(14)
                        .setYear(6650)
                        .setLongTimestamp(468406876880768L))
                .setInnerMessage1(Bag.ProtoWithAnnotations.InnerMessage.newBuilder()
                        .setNIdCt(12)
                        .setContent(Bag.ProtoWithAnnotations.InnerMessage.Type.IMAGE)
                        .addData(Bag.ProtoWithAnnotations.InnerMessage.Data.newBuilder()
                                .setData("data$$")
                                .setWidth(200))
                        .addData(Bag.ProtoWithAnnotations.InnerMessage.Data.newBuilder()
                                .setHeight(56)))
                .build();
        String json = gsonWithLowerHyphen.create().toJson(proto);
    }


    public boolean pase(InputStream is) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            ParserHandler handler = new ParserHandler();
            parser.parse(is, handler);

            return true;
        } catch (IOException | ParserConfigurationException | SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    private class ParserHandler extends DefaultHandler {

        public ParserHandler() {
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();

        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
        }

    }

}