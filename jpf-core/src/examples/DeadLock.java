

    public class DeadLock {

        static int BUFFER_SIZE = 1;
        static int N_PRODUCERS = 4;
        static int N_CONSUMERS = 4;

        static Object DATA = "fortytwo";

        //--- the bounded buffer implementation
        protected Object[] buf;
        protected int in = 0;
        protected int out= 0;
        protected int count= 0;
        protected int size;

        public DeadLock(int size) {
            this.size = size;
            buf = new Object[size];
        }

        public synchronized void put(Object o) throws InterruptedException {
            while (count == size) {
                wait();
            }
            buf[in] = o;
            //System.out.println("PUT from " + Thread.currentThread().getName());
            ++count;
            in = (in + 1) % size;
            notify(); // if this is not a notifyAll() we might notify the wrong waiter
        }

        public synchronized Object get() throws InterruptedException {
            while (count == 0) {
                wait();
            }
            Object o = buf[out];
            buf[out] = null;
            //System.out.println("GET from " + Thread.currentThread().getName());
            --count;
            out = (out + 1) % size;
            notify(); // if this is not a notifyAll() we might notify the wrong waiter
            return (o);
        }

        //--- the producer
        static class Producer extends Thread {
            static int nProducers = 1;
            DeadLock buf;

            Producer(DeadLock b) {
                buf = b;
                setName("P" + nProducers++);
            }

            @Override
            public void run() {
                try {
                    while(true) {
                        // to ease state matching, we don't put different objects in the buffer
                        buf.put(DATA);
                    }
                } catch (InterruptedException e){}
            }
        }

        //--- the consumer
        static class Consumer extends Thread {
            static int nConsumers = 1;
            DeadLock buf;

            Consumer(DeadLock b) {
                buf = b;
                setName( "C" + nConsumers++);
            }

            @Override
            public void run() {
                try {
                    while(true) {
                        Object tmp = buf.get();
                    }
                } catch(InterruptedException e ){}
            }
        }

        //--- the test driver
        public static void main(String [] args) {
            readArguments( args);
            //System.out.printf("running Deadlock with buffer-size %d, %d producers and %d consumers\n", BUFFER_SIZE, N_PRODUCERS, N_CONSUMERS);

            DeadLock buf = new DeadLock(BUFFER_SIZE);

            for (int i=0; i<N_PRODUCERS; i++) {
                new Producer(buf).start();
            }
            for (int i=0; i<N_CONSUMERS; i++) {
                new Consumer(buf).start();
            }
        }

        static void readArguments (String[] args){
            if (args.length > 0){
                BUFFER_SIZE = Integer.parseInt(args[0]);
            }
            if (args.length > 1){
                N_PRODUCERS = Integer.parseInt(args[1]);
            }
            if (args.length > 2){
                N_CONSUMERS = Integer.parseInt(args[2]);
            }
        }
    }

