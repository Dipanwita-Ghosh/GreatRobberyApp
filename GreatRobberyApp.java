import java.util.Random;

abstract class Person{
    private String name;
    private String nickname;
    private int yearOfBirth;
    private String expertIn;
    private Item[] items;

    Person(String name, String nickname, int yearOfBirth, String expertIn, Item[] items){
        this.name = name;
        this.nickname = nickname;
        this.yearOfBirth = yearOfBirth;
        this.expertIn = expertIn;
        this.items = items;
    }

    void printBioData(){
        System.out.println("Name: "  + name);
        System.out.println("Nickname: " + nickname);
        System.out.println("Year of Birth: "+ yearOfBirth);
        System.out.println("Expert in: "+ expertIn);
        System.out.println("Items: ");
        for(int i=0; i<items.length; i++){
            System.out.println("Item name: "+items[i].getName());
            
            System.out.println("Item value: "+items[i].getValue());
        }

    }

    String getName(){
        return name;
    }
    String getNickname(){
        return nickname;
    }

}

class Item{
    private String name;
    private double value;
    Item(String name,double value){
        this.name=name;
        this.value=value;
    }

    String getName(){
        return name;
    }

    double getValue(){
        return value;
    }

}

class Criminal extends Person{  
    final static int success_percentage = 50;
    Criminal(String name, String nickname, int yearOfBirth, String expertIn, Item[] items){
        super(name, nickname, yearOfBirth, expertIn, items);
    }

    void printBioData(){
        System.out.println("Criminal person:\n");
        super.printBioData();
    }
}
class Detective extends Person{  
    final static int success_percentage = 50;
    Detective(String name, String nickname, int yearOfBirth, String expertIn, Item[] items){
        super(name, nickname, yearOfBirth, expertIn, items);
    }

    void printBioData(){
        System.out.println("Detective person:\n");
        super.printBioData();
    }
}

class Building{
    private String name;
    private Item[] items;

    Building(String name, Item items[]){
        this.name = name;
        this.items = items;
    }

    String getName(){
        return name;
    }
     
    Item[] getItems(){
        return items;
    }
}

class City {
    private Building buildings[] = new Building[4];

    public City() {
        Item[] bankItems = new Item[2];
        bankItems[0] = new Item("Letter opener", 1.5);
        bankItems[1] = new Item("Stamp",2.5);
        buildings[0] = new Building("Bank", bankItems);

        Item[] mansionItems = new Item[2];
        bankItems[0] = new Item("Pair of fancy shoes", 25.0);
        bankItems[1] = new Item("Broken glass",0.1);
        buildings[1] = new Building("Mansion", mansionItems);

        Item[] postOfficeItems = new Item[2];
        bankItems[0] = new Item("Letter to Jenny", 1.5);
        bankItems[1] = new Item("Pencil",2.0);
        buildings[2] = new Building("Postoffice", postOfficeItems);

        Item[] supermarketItems = new Item[2];
        bankItems[0] = new Item("A loaf of bread",.5);
        bankItems[1] = new Item("A bag of tea",6.5);
        buildings[3] = new Building("Supermarket", supermarketItems);

                
    }

    Building[] getBuildings() {
        return buildings;
    }

   
}

class Gang{
    private Criminal[] criminals = new Criminal[2];
    private Random generator = new Random();
    private double sumRobbedvalue = 0.0;
    Gang(){
        Item [] itemsRob= new Item[2];
        itemsRob[0]= new Item("Gun", 100);
        itemsRob[1]= new Item("Hammer", 36);
        Criminal rob = new Criminal("Rob", "001", 1993, "Cracking codes", itemsRob);
        criminals[0] = rob;

        Item [] itemsBobby= new Item[2];
        itemsBobby[0]= new Item("Knife", 25.0);
        itemsBobby[1]= new Item("Chain saw", 10);
        Criminal bobby = new Criminal("Bobby", "000", 1990, "Stealing", itemsBobby);
        criminals[1] = bobby; 
    }

    void letsRob(Building [] buildings){
        int generatedBuildingIndex = generator.nextInt(buildings.length);
        if(isSuccessfulRobbery()){
            System.out.println("The gang managed to rob the following items from the "+buildings[generatedBuildingIndex].getName()+":");
            System.out.println(buildings[generatedBuildingIndex].getItems());
            for (Item item:buildings[generatedBuildingIndex].getItems()){
                System.out.println("Item name: "+ item.getName());
                sumRobbedvalue += item.getValue();            
            }
        }else{
            System.out.println("The gang tried to rob the: "+buildings[generatedBuildingIndex].getName()+"but the failed." );
        }
    }

    double getSumRobbedValue(){
        return sumRobbedvalue;
    }

    void getGanginfo(){
        for(Criminal criminal : criminals){
            criminal.printBioData();
        }
    
    }

    boolean isSuccessfulRobbery(){
        int generatedNumber= generator.nextInt(100);
        int criminalSuccessRate= criminals.length*Criminal.success_percentage;
        if(generatedNumber< criminalSuccessRate){
            return true;
        }
        return false;
    }


}
class Police{
    private Detective adamPalmer;

    Police(){
        Item[] adamItems = new Item[2];
        adamItems[0] = new Item("Gun", 350);
        adamItems[1] = new Item("Knife", 25);
        adamPalmer = new Detective("Adam Palmer", "007", 1967, "Solving cases", adamItems);
    }

    boolean areCriminalsCaught(){
        Random generator = new Random();
        int generatedNumber = generator.nextInt(100);
        if (generatedNumber >= Detective.success_percentage){
            return false;
        }
        return true;
    }
    
    boolean catchCriminals(Gang gang){
        if(areCriminalsCaught()){
            System.out.println(adamPalmer.getName() +" managed to catch the thief.");

            if (gang.getSumRobbedValue()>0){
                System.out.println("The stolen items are recovered . The overall value is estimated to $" + gang.getSumRobbedValue());
            }
            else{
                System.out.println("The gang couldn't steal anything.");
            }
            return true;
        }
        else{
            System.out.println(adamPalmer.getName() + "could not manage to catch the gang.");
            System.out.println("They managed to steal the iteams of value $"+ gang.getSumRobbedValue());

            return false;
        }
    
    }
}

public class GreatRobberyApp{
    public static void main(String[] args) {
        City city = new City();
        Gang gang = new Gang();
        Police police = new Police();

        gang.getGanginfo();
        do{
            gang.letsRob(city.getBuildings());
        } while (police.areCriminalsCaught() == false);

        police.catchCriminals(gang);
    }
}