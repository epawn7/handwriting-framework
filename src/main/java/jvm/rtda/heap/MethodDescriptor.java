package jvm.rtda.heap;

import java.util.ArrayList;

public class MethodDescriptor {

    ArrayList<String> parameterTypes;

    String returnType;

    int index;

    String descriptor;

    public MethodDescriptor(String descriptor) {
        this.descriptor = descriptor;
        parameterTypes = new ArrayList<>();
        this.index = 0;
        parseMethodDescriptor(descriptor);
    }

    private void parseMethodDescriptor(String descriptor) {
        startParam();
        int paramEndIndex = descriptor.indexOf(')');
        while (index < paramEndIndex) {
            String param = parseField();
            if (param.equals("")) {
                break;
            }
            parameterTypes.add(param);
        }
        index++;
        parseReturnType();
    }

    private void parseReturnType() {
        char f = descriptor.charAt(index);
        if ('V' == f) {
            returnType = "V";
            return;
        }
        returnType = parseField();
    }

    private String parseField() {
        char f = descriptor.charAt(index);
        switch (f) {
            case 'B':
                index++;
                return "B";
            case 'C':
                index++;
                return "C";
            case 'D':
                index++;
                return "D";
            case 'F':
                index++;
                return "F";
            case 'I':
                index++;
                return "I";
            case 'J':
                index++;
                return "J";
            case 'S':
                index++;
                return "S";
            case 'Z':
                index++;
                return "Z";
            case 'L':
                int endIndex = descriptor.indexOf(";", index);
                String str = descriptor.substring(index, endIndex);
                index = endIndex + 1;
                return str;
            case '[':
                index++;
                return "[" + parseField();
            default:
                return "";
        }
    }

    private void parseObject() {

    }

    private void startParam() {
        if (descriptor.charAt(index) == '(') {
            index++;
        } else {
            parseError();
        }
    }


    private void parseError() {
        throw new RuntimeException("解析失败");
    }

}
