// automatically generated by the FlatBuffers compiler, do not modify

package com.fbs.app.generated;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class ObjectLevel extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_2_0_0(); }
  public static ObjectLevel getRootAsObjectLevel(ByteBuffer _bb) { return getRootAsObjectLevel(_bb, new ObjectLevel()); }
  public static ObjectLevel getRootAsObjectLevel(ByteBuffer _bb, ObjectLevel obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public ObjectLevel __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String name() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer nameAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer nameInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }
  public String id() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer idAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public ByteBuffer idInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 6, 1); }

  public static int createObjectLevel(FlatBufferBuilder builder,
      int nameOffset,
      int idOffset) {
    builder.startTable(2);
    ObjectLevel.addId(builder, idOffset);
    ObjectLevel.addName(builder, nameOffset);
    return ObjectLevel.endObjectLevel(builder);
  }

  public static void startObjectLevel(FlatBufferBuilder builder) { builder.startTable(2); }
  public static void addName(FlatBufferBuilder builder, int nameOffset) { builder.addOffset(0, nameOffset, 0); }
  public static void addId(FlatBufferBuilder builder, int idOffset) { builder.addOffset(1, idOffset, 0); }
  public static int endObjectLevel(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public ObjectLevel get(int j) { return get(new ObjectLevel(), j); }
    public ObjectLevel get(ObjectLevel obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}

