package com.datapine.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.datapine.domain.Item;
import com.datapine.service.ItemService;
import com.datapine.util.ContextUtil;

@Controller
public class ItemController {

    @Autowired private ItemService itemService;

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public String listItems(Model model) {
        model.addAttribute("items", itemService.findAllItems());
        return "item/list";
    }

    @RequestMapping(value = "/item/create", method = RequestMethod.GET)
    public String viewCreateItem() {
        return "item/create";
    }

    @RequestMapping(value = "/item", method = RequestMethod.POST)
    public String createItem(@RequestParam("name") String name, @RequestParam("description") String description, Model model) {
        itemService.save(new Item(ContextUtil.getUserEmail(), name, description));

        return listItems(model);
    }

}
