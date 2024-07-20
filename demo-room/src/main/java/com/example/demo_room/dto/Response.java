package com.example.demo_room.dto;




import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


    @NoArgsConstructor
    @Getter
    @Setter
    public class Response {

private String message;
private boolean status;

        public Response(String message, boolean status) {
            this.status = status;
            this.message = message;
        }
        public String getMessage(){
            return message;
        }

        public boolean isStatus(){
            return status;
        }

    }
