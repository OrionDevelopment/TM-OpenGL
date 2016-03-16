

package com.smithsgaming.transportmanager.network.message;

import com.google.common.base.*;
import org.jnbt.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by marcf on 3/14/2016.
 */
public abstract class NBTPayloadMessage extends TMNetworkingMessage {
    private Tag nbtTag;

    public NBTPayloadMessage() {
    }

    public NBTPayloadMessage(Tag payload) {
        nbtTag = payload;
    }

    public Tag getPayLoad() {
        return nbtTag;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        Stopwatch stopwatch = Stopwatch.createStarted();

        NBTUncompressedOutputStream stream = new NBTUncompressedOutputStream(out);
        stream.writeTag(nbtTag);
        stream.close();

        System.out.println("   ==> Finished compressing in: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms.");
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        Stopwatch stopwatch = Stopwatch.createStarted();

        NBTUncompressedInputStream stream = new NBTUncompressedInputStream(in);
        nbtTag = stream.readTag();
        stream.close();

        System.out.println("   ==> Finished reading in: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms.");
    }

    public class NBTUncompressedOutputStream {
        private final DataOutputStream os;

        public NBTUncompressedOutputStream (OutputStream os) throws IOException {
            this.os = new DataOutputStream(os);
        }

        public void writeTag (Tag tag) throws IOException {
            int type = NBTUtils.getTypeCode(tag.getClass());
            String name = tag.getName();
            byte[] nameBytes = name.getBytes(NBTConstants.CHARSET);
            this.os.writeByte(type);
            this.os.writeShort(nameBytes.length);
            this.os.write(nameBytes);
            if (type == 0) {
                throw new IOException("Named TAG_End not permitted.");
            } else {
                this.writeTagPayload(tag);
            }
        }

        private void writeTagPayload (Tag tag) throws IOException {
            int type = NBTUtils.getTypeCode(tag.getClass());
            switch (type) {
                case 0:
                    this.writeEndTagPayload((EndTag) tag);
                    break;
                case 1:
                    this.writeByteTagPayload((ByteTag) tag);
                    break;
                case 2:
                    this.writeShortTagPayload((ShortTag) tag);
                    break;
                case 3:
                    this.writeIntTagPayload((IntTag) tag);
                    break;
                case 4:
                    this.writeLongTagPayload((LongTag) tag);
                    break;
                case 5:
                    this.writeFloatTagPayload((FloatTag) tag);
                    break;
                case 6:
                    this.writeDoubleTagPayload((DoubleTag) tag);
                    break;
                case 7:
                    this.writeByteArrayTagPayload((ByteArrayTag) tag);
                    break;
                case 8:
                    this.writeStringTagPayload((StringTag) tag);
                    break;
                case 9:
                    this.writeListTagPayload((ListTag) tag);
                    break;
                case 10:
                    this.writeCompoundTagPayload((CompoundTag) tag);
                    break;
                default:
                    throw new IOException("Invalid tag type: " + type + ".");
            }

        }

        private void writeByteTagPayload (ByteTag tag) throws IOException {
            this.os.writeByte(tag.getValue().byteValue());
        }

        private void writeByteArrayTagPayload (ByteArrayTag tag) throws IOException {
            byte[] bytes = tag.getValue();
            this.os.writeInt(bytes.length);
            this.os.write(bytes);
        }

        private void writeCompoundTagPayload (CompoundTag tag) throws IOException {
            Iterator var3 = tag.getValue().values().iterator();

            while (var3.hasNext()) {
                Tag childTag = (Tag) var3.next();
                this.writeTag(childTag);
            }

            this.os.writeByte(0);
        }

        private void writeListTagPayload (ListTag tag) throws IOException {
            Class clazz = tag.getType();
            List tags = tag.getValue();
            int size = tags.size();
            this.os.writeByte(NBTUtils.getTypeCode(clazz));
            this.os.writeInt(size);

            for (int i = 0; i < size; ++i) {
                this.writeTagPayload((Tag) tags.get(i));
            }

        }

        private void writeStringTagPayload (StringTag tag) throws IOException {
            byte[] bytes = tag.getValue().getBytes(NBTConstants.CHARSET);
            this.os.writeShort(bytes.length);
            this.os.write(bytes);
        }

        private void writeDoubleTagPayload (DoubleTag tag) throws IOException {
            this.os.writeDouble(tag.getValue().doubleValue());
        }

        private void writeFloatTagPayload (FloatTag tag) throws IOException {
            this.os.writeFloat(tag.getValue().floatValue());
        }

        private void writeLongTagPayload (LongTag tag) throws IOException {
            this.os.writeLong(tag.getValue().longValue());
        }

        private void writeIntTagPayload (IntTag tag) throws IOException {
            this.os.writeInt(tag.getValue().intValue());
        }

        private void writeShortTagPayload (ShortTag tag) throws IOException {
            this.os.writeShort(tag.getValue().shortValue());
        }

        private void writeEndTagPayload (EndTag tag) {
        }

        public void close () throws IOException {
            this.os.close();
        }
    }

    public class NBTUncompressedInputStream {
        private final DataInputStream is;

        public NBTUncompressedInputStream (InputStream is) throws IOException {
            this.is = new DataInputStream(is);
        }

        public Tag readTag () throws IOException {
            return this.readTag(0);
        }

        private Tag readTag (int depth) throws IOException {
            int type = this.is.readByte() & 255;
            String name;
            if (type != 0) {
                int nameLength = this.is.readShort() & '\uffff';
                byte[] nameBytes = new byte[nameLength];
                this.is.readFully(nameBytes);
                name = new String(nameBytes, NBTConstants.CHARSET);
            } else {
                name = "";
            }

            return this.readTagPayload(type, name, depth);
        }

        private Tag readTagPayload (int type, String name, int depth) throws IOException {
            int length;
            byte[] bytes;
            Tag tag;
            switch (type) {
                case 0:
                    if (depth == 0) {
                        throw new IOException("TAG_End found without a TAG_Compound/TAG_List tag preceding it.");
                    }

                    return new EndTag();
                case 1:
                    return new ByteTag(name, this.is.readByte());
                case 2:
                    return new ShortTag(name, this.is.readShort());
                case 3:
                    return new IntTag(name, this.is.readInt());
                case 4:
                    return new LongTag(name, this.is.readLong());
                case 5:
                    return new FloatTag(name, this.is.readFloat());
                case 6:
                    return new DoubleTag(name, this.is.readDouble());
                case 7:
                    length = this.is.readInt();
                    bytes = new byte[length];
                    this.is.readFully(bytes);
                    return new ByteArrayTag(name, bytes);
                case 8:
                    short var10 = this.is.readShort();
                    bytes = new byte[var10];
                    this.is.readFully(bytes);
                    return new StringTag(name, new String(bytes, NBTConstants.CHARSET));
                case 9:
                    byte childType = this.is.readByte();
                    length = this.is.readInt();
                    ArrayList tagList = new ArrayList();

                    for (int var11 = 0; var11 < length; ++var11) {
                        tag = this.readTagPayload(childType, "", depth + 1);
                        if (tag instanceof EndTag) {
                            throw new IOException("TAG_End not permitted in a list.");
                        }

                        tagList.add(tag);
                    }

                    return new ListTag(name, NBTUtils.getTypeClass(childType), tagList);
                case 10:
                    HashMap tagMap = new HashMap();

                    while (true) {
                        tag = this.readTag(depth + 1);
                        if (tag instanceof EndTag) {
                            return new CompoundTag(name, tagMap);
                        }

                        tagMap.put(tag.getName(), tag);
                    }
                default:
                    throw new IOException("Invalid tag type: " + type + ".");
            }
        }

        public void close () throws IOException {
            this.is.close();
        }
    }
}
