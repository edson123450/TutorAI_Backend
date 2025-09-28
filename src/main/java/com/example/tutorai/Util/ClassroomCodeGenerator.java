package com.example.tutorai.Util;

import com.example.tutorai.Classroom.Domain.Classroom;

import java.security.SecureRandom;

public final class ClassroomCodeGenerator {
    private static final String ALPHABET="ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final SecureRandom RND= new SecureRandom();

    private ClassroomCodeGenerator(){}
    public static String generate(){
        int len=8;
        StringBuilder sb= new StringBuilder(len);
        for (int i=0;i<len;i++){
            sb.append(ALPHABET.charAt(RND.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

}
