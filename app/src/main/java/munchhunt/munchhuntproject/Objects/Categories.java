package munchhunt.munchhuntproject.Objects;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class Categories {
    int thai0, bubbleTea1, desserts2, indian3, burgers4, british5, breakfastBrunch6, italian7, americanNew8, middleEastern9, vegetarian10, sushiBars11, hotDogs12, vegan13, pizza14, coffeeTea15, mediterranean16, seafood17, fastFood18, mexican19, sandwiches20, greek21, vietnamese22, poke23, gastropubs24, irish25, cafes26, korean27, salad28, bbq29;
    ArrayList<Integer> c = new ArrayList<Integer>();
    public Categories()
    {}

    public Categories(int thai0, int bubbleTea1, int desserts2, int indian3, int burgers4, int british5, int breakfastBrunch6, int italian7, int americanNew8, int middleEastern9, int vegetarian10, int sushiBars11, int hotDogs12, int vegan13, int pizza14, int coffeeTea15, int mediterranean16, int seafood17, int fastFood18, int mexican19, int sandwiches20, int greek21, int vietnamese22, int poke23, int gastropubs24, int irish25, int cafes26, int korean27, int salad28, int bbq29){
        this.thai0 = thai0;
        this.bubbleTea1 = bubbleTea1;
        this.desserts2 = desserts2;
        this.indian3 = indian3;
        this.burgers4 = burgers4;
        this.british5 = british5;
        this.breakfastBrunch6 = breakfastBrunch6;
        this.italian7 = italian7;
        this.americanNew8 = americanNew8;
        this.middleEastern9 = middleEastern9;
        this.vegetarian10 = vegetarian10;
        this.sushiBars11 = sushiBars11;
        this.hotDogs12 = hotDogs12;
        this.vegan13 = vegan13;
        this.pizza14 = pizza14;
        this.coffeeTea15 = coffeeTea15;
        this.mediterranean16 = mediterranean16;
        this.seafood17 = seafood17;
        this.fastFood18 = fastFood18;
        this.mexican19 = mexican19;
        this.sandwiches20 = sandwiches20;
        this.greek21 = greek21;
        this.vietnamese22 = vietnamese22;
        this.poke23 = poke23;
        this.gastropubs24 = gastropubs24;
        this.irish25 = irish25;
        this.cafes26 = cafes26;
        this.korean27 = korean27;
        this.salad28 = salad28;
        this.bbq29 = bbq29;
        c.add(thai0);
        c.add(bubbleTea1);
        c.add(desserts2);
        c.add(indian3);
        c.add(burgers4);
        c.add(british5);
        c.add(breakfastBrunch6);
        c.add(italian7);
        c.add(americanNew8);
        c.add(middleEastern9);
        c.add(vegetarian10);
        c.add(sushiBars11);
        c.add(hotDogs12);
        c.add(vegan13);
        c.add(pizza14);
        c.add(coffeeTea15);
        c.add(mediterranean16);
        c.add(seafood17);
        c.add(fastFood18);
        c.add(mexican19);
        c.add(sandwiches20);
        c.add(greek21);
        c.add(vietnamese22);
        c.add(poke23);
        c.add(gastropubs24);
        c.add(irish25);
        c.add(cafes26);
        c.add(korean27);
        c.add(salad28);
        c.add(bbq29);

    }

    public void updateArrayist()
    {
        c.set(0,thai0);
        c.set(1, bubbleTea1);
        c.set(2, desserts2);
        c.set(3, indian3);
        c.set(4, burgers4);
        c.set(5, british5);
        c.set(6, breakfastBrunch6);
        c.set(7, italian7);
        c.set(8, americanNew8);
        c.set(9,middleEastern9);
        c.set(10, vegetarian10);
        c.set(11, sushiBars11);
        c.set(12, hotDogs12);
        c.set(13,vegan13);
        c.set(14,pizza14);
        c.set(15,coffeeTea15);
        c.set(16,mediterranean16);
        c.set(17,seafood17);
        c.set(18,fastFood18);
        c.set(19,mexican19);
        c.set(20, sandwiches20);
        c.set(21, greek21);
        c.set(22,vietnamese22);
        c.set(23,poke23);
        c.set(24,gastropubs24);
        c.set(25,irish25);
        c.set(26,cafes26);
        c.set(27,korean27);
        c.set(28, salad28);
        c.set(29, bbq29);
    }
    public int getThai0()
    {
        return thai0;
    }
    public int getBubbleTea()
    {
        return bubbleTea1;
    }
    public int getDesserts()
    {
        return desserts2;
    }
    public int getIndian()
    {
        return indian3;
    }
    public int getBurgers() {
        return burgers4;
    }

    public int getBritish() {
        return british5;
    }

    public int getBreakfastBrunch()
    {
        return breakfastBrunch6;
    }

    public int getItalian()
    {
        return italian7;
    }
    public int getAmericanNew()
    {
        return  americanNew8;
    }
    public int getMiddleEastern()
    {
        return middleEastern9;
    }
    public int getVegetarian()
    {
        return vegetarian10;
    }
    public int getSushiBars()
    {
        return  sushiBars11;
    }
    public int getHotDogs()
    {
        return hotDogs12;
    }
    public int getVegan()
    {
        return vegan13;
    }
    public int getPizza()
    {
        return pizza14;
    }
    public int getCoffeeTea(){
        return coffeeTea15;
    }
    public int getMediterranean()
    {
        return mediterranean16;
    }
    public int getSeafood()
    {
        return seafood17;
    }
    public int getFastFood()
    {
        return fastFood18;
    }
    public int getMexican()
    {
        return mexican19;
    }
    public int getSandwiches()
    {
        return sandwiches20;
    }
    public int getGreek()
    {
        return greek21;
    }
    public int getVietnamese()
    {
        return vietnamese22;
    }
    public int getPoke()
    {
        return poke23;
    }
    public int getGastropubs()
    {
        return gastropubs24;
    }
    public int getIrish()
    {
        return irish25;
    }
    public int getCafes()
    {
        return cafes26;
    }
    public int getKorean()
    {
        return korean27;
    }
    public int getSalad()
    {
        return salad28;
    }
    public int getBbq29(){
        return bbq29;
    }

    public void match(String alias)
    {
        /*
        Indian
        Coffee & Tea
        Vegetarian
        Middle Eastern
        Desserts
        Poke
        Cafes
        Pizza
        Burgers
        Salad
        Sushi Bars
        Greek
        Vietnamese
        Seafood
        American (New)
        Mexican
        Korean
        Mediterranean
        Vegan
        Bubble Tea
        Thai
        Hot Dogs
        Breakfast & Brunch
        Sandwiches
        Gastropubs
        Italian
        Irish
        British
         */

        /*if (alias.equalsIgnoreCase("Indian"))
        {
            indian3++;
        }*/
        if (alias.equalsIgnoreCase("Coffee & Tea"))
        {
            coffeeTea15++;
        }
        if (alias.equalsIgnoreCase("Vegetarian"))
        {
            vegetarian10++;
        }
        if(alias.equalsIgnoreCase("Middle Eastern"))
        {
            middleEastern9++;
        }
        /*    *//*if(alias.equalsIgnoreCase("Desserts"))
        {
            desserts2++;
        }*//*
        if (alias.equalsIgnoreCase("Poke"))
        {
            poke23++;
        }
        if (alias.equalsIgnoreCase("Cafes"))
        {
            cafes26++;
        }*/
        if (alias.equalsIgnoreCase("Pizza"))
        {
            pizza14++;
        }
        if(alias.equalsIgnoreCase("Burgers"))
        {
            burgers4++;
        }
      /*  if(alias.equalsIgnoreCase("Salad"))
        {
            salad28++;
        }*/
        if(alias.equalsIgnoreCase("Sushi Bars"))
        {
            sushiBars11++;
        }
      /*  if(alias.equalsIgnoreCase("Greek"))
        {
            greek21++;
        }
        if(alias.equalsIgnoreCase("Vietnamese"))
        {
            vietnamese22++;
        }*/
        if(alias.equalsIgnoreCase("Seafood"))
        {
            seafood17++;
        }
        if(alias.equalsIgnoreCase("American (New)"))
        {
            americanNew8++;
        }
        if(alias.equalsIgnoreCase("Mexican"))
        {
            mexican19++;
        }
     /*   if(alias.equalsIgnoreCase("Korean"))
        {
            korean27++;
        }
        if(alias.equalsIgnoreCase("Mediterranean"))*/
        {
            mediterranean16++;
        }
        if(alias.equalsIgnoreCase("Vegan"))
        {
            vegan13++;
        }
    /*    if(alias.equalsIgnoreCase("Bubble Tea"))
        {
            bubbleTea1++;
        }
        if(alias.equalsIgnoreCase("Thai"))
        {
            thai0++;
        }*/
        if (alias.equalsIgnoreCase("Hot Dogs"))
        {
            hotDogs12++;
        }
        if(alias.equalsIgnoreCase("Breakfast & Brunch"))
        {
            breakfastBrunch6++;
        }
     /*   if(alias.equalsIgnoreCase("Sandwiches"))
        {
            sandwiches20++;
        }
        if(alias.equalsIgnoreCase("Gastropubs"))
        {
            gastropubs24++;
        }*/
        if(alias.equalsIgnoreCase("Italian"))
        {
            italian7++;
        }
        if(alias.equalsIgnoreCase("Irish"))
        {
            irish25++;
        }
        if(alias.equalsIgnoreCase("British")) {
            british5++;
        }
     /*   if (alias.equalsIgnoreCase("Fast Food"))
        {
            fastFood18++;
        }*/
        if (alias.equalsIgnoreCase("Barbeque"))
        {
            bbq29++;
        }

    }

    public ArrayList<String> getResult()
    {
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<Integer> index = new ArrayList<Integer>();
        Random random = new Random();
        int max1 = c.get(0), max2 = c.get(0), max3 = c.get(0);
        int maxIndex1 = 0, maxIndex2 = 0, maxIndex3 = 0;

        for (int x = 0; x < c.size(); x++) {
            int newnummber = c.get(x);

            if (newnummber > max1) {
                max1 = newnummber;
                maxIndex1 = x;
            }
        }

        for (int y = 0; y < c.size(); y++)
        {
            int newnumber = c.get(y);

            if (y != maxIndex1) {

                if (newnumber > max2) {
                    max2 = newnumber;
                    maxIndex2 = y;
                }
            }
        }

        for (int z = 0; z < c.size(); z++ )
        {
            int newnumber = c.get(z);

            if (z != maxIndex1 && z != maxIndex2)
            {
                if (newnumber > max3)
                {
                    max3 = newnumber;
                    maxIndex3 = z;
                }
            }
        }

        Log.d("MAX1", maxIndex1 + "");
        Log.d("MAXVAL1", max1 + "");
        Log.d("MAX2", maxIndex2 + "");
        Log.d("MAXVAL2", max2 + "");
        Log.d("MAX3", maxIndex3 + "");
        Log.d("MAXVAL3", max3 + "");

        for (int i = 0 ; i < 4; i++) {
            int min = 0;
            boolean value = true;

            do {
                min = random.nextInt(28 - 1 + 1) + 1;

                if (min != maxIndex1 && min != maxIndex2 && min != maxIndex3 )
                {
                    value = false;
                }
            }while (value);





/*
            if (min == 0) {
                result.add("Thai");
            }
            if (min == 1) {
                result.add("Bubble Tea");

            }
            if (min == 2) {
                result.add("Desserts");
            }
            if (min == 3) {
                result.add("Indian");
            }*/
            if (min == 4) {
                result.add("Burgers");
            }
            if (min == 5) {
                result.add("British");
            }
            if (min == 6) {
                result.add("Breakfast & Brunch");
            }
            if (min == 7) {
                result.add("Italian");
            }
            if (min == 8) {
                result.add("American (New)");
            }
            if (min == 9) {
                result.add("Middle Eastern");
            }
            if (min == 10) {
                result.add("Vegetarian");
            }
            if (min == 11) {
                result.add("Sushi Bars");
            }
            if (min == 12) {
                result.add("Hot Dogs");
            }
            if (min == 13) {
                result.add("Vegan");
            }
            if (min == 14) {
                result.add("Pizza");
            }
            if (min == 15) {
                result.add("Coffee & Tea");
            }
            if (min == 16) {
                result.add("Mediterranean");
            }
            if (min == 17) {
                result.add("Seafood");
            }
            if (min == 18) {
                result.add("Fast Food");
            }
            if (min == 19) {
                result.add("Mexican");
            }
            if (min == 20) {
                result.add("Sandwiches");
            }
            /*if (min == 21) {
                result.add("Greek");
            }
            if (min == 22) {
                result.add("Vietnamese");
            }
            if (min == 23) {
                result.add("Poke");
            }
            if (min == 24) {
                result.add("Gastropubs");
            }
            if (min == 25) {
                result.add("Irish");
            }
            if (min == 26) {
                result.add("Cafes");
            }
            if (min == 27) {
                result.add("Korean");
            }
            if (min == 28) {
                result.add("Salad");
            }*/
            if (min == 29)
            {
                result.add("Barbeque");
            }
        }



        return result;
    }
}
