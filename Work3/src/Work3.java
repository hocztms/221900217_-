import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Work3 {
    public static void main(String[] args) {
        FriedChickenRestaurant west2FriedChickenRestauran = new West2FriedChickenRestauran(20);

        //创建2个饮料
        LocalDate d1 = LocalDate.of(2020,1,1);
        LocalDate d2 = LocalDate.of(2020,1,2);
        Juice orange = new Juice("橙汁",10,d1);
        Beer vodka = new Beer("小鸟伏特加",20,d1, 0.5f);
        System.out.println("用全参函数创建2个饮料分别为\n"+ orange.toString() + "\n" + vodka.toString() +"\n" + "并测试是否过期函数");
        System.out.println(orange.name + orange.safedate(d2) + "\n" + vodka.name + vodka.safedate(d2));
        System.out.println("测试成功");

        //测试卖和进货函数
        System.out.println("测试出售套餐和进货函数");
        SetMeal meal1 = new SetMeal("休闲下午餐",50,"燃情伴翅",orange);
        SetMeal meal2 = new SetMeal("要命下午餐",100,"吮指原味鸡",vodka);
        west2FriedChickenRestauran.getmeal(orange,1);
        west2FriedChickenRestauran.getmeal(vodka,1);
        west2FriedChickenRestauran.sellmeal(meal1);
        west2FriedChickenRestauran.sellmeal(meal2);

    }

}

abstract class  Drinks{
    protected String name;
    protected double d_cost;
    protected LocalDate prod_date;
    protected int qua_date;
    public  Drinks(String name,double d_cost,LocalDate prod_date,int qua_date){
        this.d_cost = d_cost;
        this.name=name;
        this.prod_date=prod_date;
        this.qua_date=qua_date;
    }
    boolean safedate(LocalDate d2){
        LocalDate d1 = prod_date;
        return !d1.plusDays(qua_date).isAfter(d2);
    }
    @Override
    public abstract String toString() ;
}

class Beer extends Drinks{
    protected float alcohol;
    public Beer(String name,double d_cost,LocalDate prod_data,float alcohol){
        super(name,d_cost,prod_data,30);
        this.alcohol=alcohol;
    }

    @Override
    public String toString() {
        return "啤酒："+ " " + name + " 生产日期:" + prod_date +" 保质期" +qua_date + " 度数:" + alcohol +" " +d_cost + "元";
    }
}

class Juice extends Drinks{
    public Juice(String name,double d_cost,LocalDate prod_data){
        super(name,d_cost,prod_data,2);
    }

    @Override
    public String toString() {
        return "果汁："+ " " + name + " 生产日期:" + prod_date +" 保质期" +qua_date + " " +d_cost + "元";
    }
}

class SetMeal {
    protected String name;
    protected double cost;
    protected String fc_name;
    protected Drinks drinks;
    public SetMeal(String name,double cost,String fc_name,Drinks drinks){
        this.name=name;
        this.drinks=drinks;
        this.fc_name=fc_name;
        this.cost=cost;
    }
    public String toString(){
        return name +" " + fc_name +" "+ drinks.name +" 计" + cost + "元";
    }
}

interface FriedChickenRestaurant{
    void sellmeal(SetMeal tmpmeal);
    void getmeal(Drinks drinks,int nums);
}

class IngredientSortOutException extends RuntimeException{
    public IngredientSortOutException(String messge){
        super(messge);
    }
}

class OverdraftBalanceException extends  RuntimeException{
    public OverdraftBalanceException(String messge){
        super(messge);
    }

}
class West2FriedChickenRestauran implements FriedChickenRestaurant{
    double countmoney;
    public List<Beer> beerList = new ArrayList<>();
    public List<Juice> juiceList = new ArrayList<>();
    public  static  List<SetMeal> meals = new ArrayList<>();
    //实用list不仅可以像数组一样操作 还封装了删除和插入方法 实现动态 非常之方便；而且内存少又快与linklist相比同样查找, 时间复杂度都是O(N), 但是数组要比链表快

    public West2FriedChickenRestauran (double countmoney){
        this.countmoney = countmoney;
    }
    static {
        System.out.println("创建炸鸡店 20块钱作为本金测试数据 设置套餐 本次测试为直接设置 不添加输入设置功能");
        LocalDate d1 = LocalDate.of(2020,1,1);
        Juice orange = new Juice("橙汁",10,d1);
        SetMeal meal1 = new SetMeal("休闲下午餐",50,"燃情伴翅",orange);
        Beer vodka = new Beer("小鸟伏特加",20,d1, 0.5f);
        SetMeal meal2 = new SetMeal("要命下午餐",100,"吮指原味鸡",vodka);
        meals.add(meal1);
        meals.add(meal2);
        System.out.println("设置成功 已设置如下套餐");
        System.out.println(meals.get(0).toString());
        System.out.println(meals.get(1).toString());

    }



    private void use(Beer beer){
        for (int i=0,j=beerList.size();i<j;i++){
            if (beerList.get(i).name.equals(beer.name)){
                beerList.remove(i);
                System.out.println("sell success");
                return;
            }
        }
        IngredientSortOutException error1 = new IngredientSortOutException("失败" + beer.name+"售完");
        System.out.println(error1.toString());
    }

    private void use(Juice juice){
        for (int i=0,j=juiceList.size();i<j;i++){
            if (juiceList.get(i).name.equals(juice.name)){
                juiceList.remove(i);
                System.out.println("sell success");
                return;
            }
        }
        IngredientSortOutException error1 = new IngredientSortOutException("失败" + juice.name + "售完");
        System.out.println(error1.toString());
    }

    public void sellmeal(SetMeal tmpmeal){
        boolean result = tmpmeal.drinks instanceof Beer;
        if (result) {
            Beer beer = (Beer)tmpmeal.drinks;
            use(beer);

        }
       else {
            Juice juice = (Juice)tmpmeal.drinks;
            use(juice);
        }
    }
    public void getmeal(Drinks drinks,int nums) {
            boolean result = drinks instanceof Beer;
            if (result) {
                Beer beer = (Beer) drinks;
                for (int i=0;i<nums;i++) {
                    countmoney-=beer.d_cost;
                    if (countmoney>=0) beerList.add(beer);
                    else {
                        countmoney+=beer.d_cost;
                        OverdraftBalanceException error2 = new OverdraftBalanceException("失败 进货费用超出拥有余额 没钱了 买不起了需要" + (beer.d_cost*nums-countmoney));
                        System.out.println(error2.toString());
                    }
                }
            } else {
                Juice juice = (Juice) drinks;
                for (int i=0;i<nums;i++){
                    countmoney-=juice.d_cost;
                    if (countmoney>=0) juiceList.add(juice);
                    else {
                        countmoney+=juice.d_cost;
                        OverdraftBalanceException error2 = new OverdraftBalanceException("失败 进货费用超出拥有余额 没钱了 买不起了需要" + (juice.d_cost*nums-countmoney));
                        System.out.println(error2.toString());
                    }
                }
            }
    }

}

