public class ExceptionExample {
    public static void main(String[] args) {
        Integer[] arr = new Integer[2];
        arr[0] = 0;
        arr[1] = 1;
        arr[2] = 2;
        for(Integer i : arr){
            System.out.println(i);
        }
    }
}
