// automatically generated by the FlatBuffers compiler, do not modify

package com.fbs.app.generated;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class Animal extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_2_0_0(); }
  public static Animal getRootAsAnimal(ByteBuffer _bb) { return getRootAsAnimal(_bb, new Animal()); }
  public static Animal getRootAsAnimal(ByteBuffer _bb, Animal obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public Animal __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String name() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer nameAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer nameInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }
  public String sound() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer soundAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public ByteBuffer soundInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 6, 1); }
  public int weight() { int o = __offset(8); return o != 0 ? bb.getShort(o + bb_pos) & 0xFFFF : 0; }
  public com.fbs.app.generated.ObjectLevel obj(int j) { return obj(new com.fbs.app.generated.ObjectLevel(), j); }
  public com.fbs.app.generated.ObjectLevel obj(com.fbs.app.generated.ObjectLevel obj, int j) { int o = __offset(10); return o != 0 ? obj.__assign(__indirect(__vector(o) + j * 4), bb) : null; }
  public int objLength() { int o = __offset(10); return o != 0 ? __vector_len(o) : 0; }
  public com.fbs.app.generated.ObjectLevel.Vector objVector() { return objVector(new com.fbs.app.generated.ObjectLevel.Vector()); }
  public com.fbs.app.generated.ObjectLevel.Vector objVector(com.fbs.app.generated.ObjectLevel.Vector obj) { int o = __offset(10); return o != 0 ? obj.__assign(__vector(o), 4, bb) : null; }

  public static int createAnimal(FlatBufferBuilder builder,
      int nameOffset,
      int soundOffset,
      int weight,
      int objOffset) {
    builder.startTable(4);
    Animal.addObj(builder, objOffset);
    Animal.addSound(builder, soundOffset);
    Animal.addName(builder, nameOffset);
    Animal.addWeight(builder, weight);
    return Animal.endAnimal(builder);
  }

  public static void startAnimal(FlatBufferBuilder builder) { builder.startTable(4); }
  public static void addName(FlatBufferBuilder builder, int nameOffset) { builder.addOffset(0, nameOffset, 0); }
  public static void addSound(FlatBufferBuilder builder, int soundOffset) { builder.addOffset(1, soundOffset, 0); }
  public static void addWeight(FlatBufferBuilder builder, int weight) { builder.addShort(2, (short)weight, (short)0); }
  public static void addObj(FlatBufferBuilder builder, int objOffset) { builder.addOffset(3, objOffset, 0); }
  public static int createObjVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startObjVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endAnimal(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }
  public static void finishAnimalBuffer(FlatBufferBuilder builder, int offset) { builder.finish(offset); }
  public static void finishSizePrefixedAnimalBuffer(FlatBufferBuilder builder, int offset) { builder.finishSizePrefixed(offset); }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public Animal get(int j) { return get(new Animal(), j); }
    public Animal get(Animal obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}

