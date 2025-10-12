package com.bikerboys.deadbeardcopy.items;

import com.bikerboys.deadbeardcopy.*;
import net.minecraft.component.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import static net.minecraft.registry.Registries.*;
import net.minecraft.util.*;

public class ModItems {



    public static final Item STONE_CUTLASS = register(
            new SwordItem(ToolMaterials.STONE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.STONE, 3, -2.4F))),
            "stone_cutlass"
    );

    public static final Item GOLD_CUTLASS = register(
            new SwordItem(ToolMaterials.GOLD, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.GOLD, 3, -2.4F))),
            "golden_cutlass"
    );



    public static Item register(Item item, String id) {
        Identifier itemID = Identifier.of(DeadbeardCopy.MOD_ID, id);

        Item registeredItem = Registry.register(ITEM, itemID, item);

        return registeredItem;
    }





    public static void initialize() {

    }
}
