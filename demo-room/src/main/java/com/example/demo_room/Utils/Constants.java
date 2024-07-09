package com.example.demo_room.Utils;

public class Constants {

        public enum ResponseCode{
            SUCCESS(200),
            FAILED(404);
            private int code;
            private ResponseCode(int code){
                this.code=code;
            }
            public int value(){
                return this.code;
            }

}}

