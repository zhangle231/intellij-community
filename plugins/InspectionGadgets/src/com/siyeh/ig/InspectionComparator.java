package com.siyeh.ig;

import com.intellij.codeInspection.LocalInspectionTool;

import java.util.Comparator;

class InspectionComparator implements Comparator<Class> {
    InspectionComparator() {
        super();
    }

    public int compare(Class class1, Class class2) {
        final LocalInspectionTool inspection1;
        final LocalInspectionTool inspection2;
        try {
            inspection1 = (LocalInspectionTool) class1.newInstance();
            inspection2 = (LocalInspectionTool)class2.newInstance();
        } catch (InstantiationException e) {
            return -1;
        } catch (IllegalAccessException e) {
            return -1;
        }
        final String groupName1 = inspection1.getGroupDisplayName();
        final String groupName2 = inspection2.getGroupDisplayName();
        final int groupNameComparison = groupName1.compareTo(groupName2);
        if (groupNameComparison != 0) {
            return groupNameComparison;
        }
        String displayName1 = inspection1.getDisplayName();
        String displayName2 = inspection2.getDisplayName();
        displayName1 = displayName1.toUpperCase();
        displayName2 = displayName2.toUpperCase();
        displayName1 = stripQuotes(displayName1);
        displayName2 = stripQuotes(displayName2);

        return displayName1.compareTo(displayName2);
    }

    private static String stripQuotes(String str) {
        if(str.indexOf((int) '\'') <0 && str.indexOf((int) '"')<0)
        {
            return str;
        }
        final int length = str.length();
        final StringBuffer buffer = new StringBuffer(length);
        for (int i = 0; i < length; i++) {
            final char ch = str.charAt(i);
            if(ch != '"' && ch != '\''){
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }
}
