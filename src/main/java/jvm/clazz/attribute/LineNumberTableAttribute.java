package jvm.clazz.attribute;

import jvm.clazz.ClassReader;

/**
 * 表存放方法的行号信息,属于调试信息，不是运行时必需的。
 * 在使用javac编译器编译Java程序时，
 * 默认会在class文件中生成这些信息。
 * LineNumberTable_attribute {
 * u2 attribute_name_index;
 * u4 attribute_length;
 * u2 line_number_table_length;
 * {   u2 start_pc;
 * u2 line_number;
 * } line_number_table[line_number_table_length];
 * }
 */
public class LineNumberTableAttribute extends AttributeInfo {

    short lineNumberTableLength;

    LineNumberTable[] lineNumberTable;

    public LineNumberTableAttribute(String attributeName) {
        super(attributeName);
    }

    @Override
    public void parseBytes(ClassReader reader) {
        lineNumberTableLength = reader.readU2ToShort();
        if (lineNumberTableLength > 0) {
            lineNumberTable = new LineNumberTable[lineNumberTableLength];
            for (int i = 0; i < lineNumberTableLength; i++) {
                lineNumberTable[i] = new LineNumberTable();
                lineNumberTable[i].startPc = reader.readU2ToShort();
                lineNumberTable[i].lineNumber = reader.readU2ToShort();
            }
        }
    }

    public static class LineNumberTable {

        short startPc;

        short lineNumber;

    }

}
