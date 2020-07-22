package com.ruscassie.video;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class FfProbeItemWriter implements ItemWriter<Integer> {
    @Override
    public void write(List<? extends Integer> list) throws Exception {
        list.forEach( item -> System.out.println("Write: "+item));
    }
}
