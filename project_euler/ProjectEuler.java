import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class ProjectEuler {

    /**
     * Problem 1: If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9.
     * The sum of these multiples is 23. Find the sum of all the multiples of 3 or 5 below 1000.
     */
    public int sumOfMultiplesOfXAndYBelowTop(int x, int y, int top) {
        int x2 = x;
        int y2 = y;
        int sum = 0;
        while (top >= x2 && top >= y2) {
            if (x2 > y2 && y2 < top) {
                sum += y2;
                y2 += y;
            } else if (y2 > x2 && x2 < top) {
                sum += x2;
                x2 += x;
            } else {
                sum += x2;
                x2 += x;
                y2 += y;
            }
        }
        return sum;
    }

    /**
     * Problem 2: Each new term in the Fibonacci sequence is generated by adding the previous two terms. By starting with 1 and 2,
     * the first 10 terms will be: 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, ...
     * By considering the terms in the Fibonacci sequence whose values do not exceed four million, find the sum of the
     * even-valued terms.
     *
     * @return sum of the even-valued Fibonacci terms.
     */
    public long sumOfEvenFibonacciTerms(int top) {
        int first = 1;
        int second = 2;
        Integer sum = 0;
        while (second <= top) {
            if (second % 2 == 0) {
                sum += second;
            }

            int secondMemory = second;
            second += first;
            first = secondMemory;
        }

        return sum;
    }

    /**
     * Problem 3: The prime factors of 13195 are 5, 7, 13 and 29.
     * What is the largest prime factor of the number 600851475143 ?
     *
     * @return list of prime factors
     */
    public List<Long> primeFactorsOf(Long number) {
        PrimeNumbers primeNumbers = new PrimeNumbers();
        List<Long> primeFactors = new ArrayList<>();

        Long prime = primeNumbers.next();
        while (prime <= number) {
            if (number % prime == 0) {
                Long divisionResult = number / prime;
                primeFactors.add(prime);
                number = divisionResult;
            }

            prime = primeNumbers.next();
        }

        return primeFactors;
    }

    /**
     * Problem 4: A palindromic number reads the same both ways. The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 × 99.
     * Find the largest palindrome made from the product of two 3-digit numbers.
     *
     * @return: A list with both digits that make the product of them the largest palindrome
     */
    public PalindromeResult largestPalindromeProduct(int from, int to) {
        List<PalindromeResult> results = new ArrayList<>();
        IntStream.rangeClosed(from, to)
                .forEach(i -> {
                    IntStream.rangeClosed(from, to).forEach(j -> {
                        if (isPalindrome(i * j)) {
                            results.add(new PalindromeResult(i, j));
                        }
                    });
                });
        return results.stream().max(Comparator.comparing(r -> r.product())).orElse(new PalindromeResult(0, 0));
    }

    public class PalindromeResult {
        int i, j;

        public PalindromeResult(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public Long product() {
            return new Long(i * j);
        }

        @Override
        public String toString() {
            return "i = " + i + " j = " + j;
        }
    }

    private boolean isPalindrome(int num) {
        String numString = String.valueOf(num);
        return new StringBuilder(numString).reverse().toString().equals(numString);
    }

    /**
     * Problem 5: 2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any remainder.
     * What is the smallest positive number that is evenly divisible by all of the numbers from 1 to 20?
     *
     * @param bottom
     * @param top
     * @return
     */
    public Long smallestMultipleForNumbersFrom(int bottom, int top) {
        return new Long(IntStream.iterate(1, i -> i + 1)
                .filter(i -> IntStream.rangeClosed(bottom, top)
                        .allMatch(d -> i % d == 0))
                .findFirst()
                .getAsInt());
    }


    /**
     * Problem 6: The sum of the squares of the first ten natural numbers is,
     * 12 + 22 + ... + 102 = 385
     * The square of the sum of the first ten natural numbers is,
     * (1 + 2 + ... + 10)2 = 552 = 3025
     * Hence the difference between the sum of the squares of the first ten natural numbers and the square of the sum is 3025 − 385 = 2640.
     * Find the difference between the sum of the squares of the first one hundred natural numbers and the square of the sum.
     *
     * @param bottom
     * @param top
     * @return
     */
    public Integer sumSquareDifference(int bottom, int top) {
        Integer sumOfTheSquares = IntStream.rangeClosed(bottom, top).map(i -> i * i).sum();

        Integer sum = IntStream.rangeClosed(bottom, top).sum();
        Integer squareOfTheSum = sum * sum;

        return squareOfTheSum - sumOfTheSquares;
    }

    /**
     * Problem 7: By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see that the 6th prime is 13.
     * What is the 10 001st prime number?
     *
     * @param position
     * @return
     */
    public Long findPrimeInPosition(int position) {
        PrimeNumbers primeNumbers = new PrimeNumbers();
        Long seed = primeNumbers.next();
        return LongStream.iterate(seed, i -> primeNumbers.next()).skip(position - 1).findFirst().getAsLong();
    }

    /**
     * Problem 8: he four adjacent digits in the 1000-digit number that have the greatest product are 9 × 9 × 8 × 9 = 5832.
     * 73167176531330624919225119674426574742355349194934
     * 96983520312774506326239578318016984801869478851843
     * 85861560789112949495459501737958331952853208805511
     * 12540698747158523863050715693290963295227443043557
     * 66896648950445244523161731856403098711121722383113
     * 62229893423380308135336276614282806444486645238749
     * 30358907296290491560440772390713810515859307960866
     * 70172427121883998797908792274921901699720888093776
     * 65727333001053367881220235421809751254540594752243
     * 52584907711670556013604839586446706324415722155397
     * 53697817977846174064955149290862569321978468622482
     * 83972241375657056057490261407972968652414535100474
     * 82166370484403199890008895243450658541227588666881
     * 16427171479924442928230863465674813919123162824586
     * 17866458359124566529476545682848912883142607690042
     * 24219022671055626321111109370544217506941658960408
     * 07198403850962455444362981230987879927244284909188
     * 84580156166097919133875499200524063689912560717606
     * 05886116467109405077541002256983155200055935729725
     * 71636269561882670428252483600823257530420752963450
     * Find the thirteen adjacent digits in the 1000-digit number that have the greatest product. What is the value of this product?
     *
     * @param adjacentDigits
     * @return
     */
    public Long largestProductInASeries(int adjacentDigits) {
        String oneKDigit = "73167176531330624919225119674426574742355349194934" +
                "96983520312774506326239578318016984801869478851843" +
                "85861560789112949495459501737958331952853208805511" +
                "12540698747158523863050715693290963295227443043557" +
                "66896648950445244523161731856403098711121722383113" +
                "62229893423380308135336276614282806444486645238749" +
                "30358907296290491560440772390713810515859307960866" +
                "70172427121883998797908792274921901699720888093776" +
                "65727333001053367881220235421809751254540594752243" +
                "52584907711670556013604839586446706324415722155397" +
                "53697817977846174064955149290862569321978468622482" +
                "83972241375657056057490261407972968652414535100474" +
                "82166370484403199890008895243450658541227588666881" +
                "16427171479924442928230863465674813919123162824586" +
                "17866458359124566529476545682848912883142607690042" +
                "24219022671055626321111109370544217506941658960408" +
                "07198403850962455444362981230987879927244284909188" +
                "84580156166097919133875499200524063689912560717606" +
                "05886116467109405077541002256983155200055935729725" +
                "71636269561882670428252483600823257530420752963450";

        return LongStream.range(0, oneKDigit.length()).map(i -> {
            if (i + adjacentDigits < oneKDigit.length()) {
                Long multiplication = LongStream.rangeClosed(i, i + adjacentDigits - 1)
                        .map(j -> new Long(String.valueOf(oneKDigit.charAt((int) j))))
                        .reduce(1, (base, index) -> base * index);
                return multiplication;
            } else {
                return 0;
            }
        }).max().orElse(0);
    }

    /**
     * Problem 9: A Pythagorean triplet is a set of three natural numbers, a < b < c, for which,
     a^2 + b^2 = c^2
     For example, 32 + 42 = 9 + 16 = 25 = 5^2.
     There exists exactly one Pythagorean triplet for which a + b + c = 1000.
     Find the product abc.
     * @param tripletsSum
     * @return
     */
    public Long specialPythagoreanTriple(int tripletsSum) {
        for(int a = 0; a <= tripletsSum; a++) {
            for(int b = a+1; b <= tripletsSum-a; b++) {
                for(int c = b+1; c <= tripletsSum-a-b; c++) {
                    if((a + b + c == tripletsSum) && ((a*a)+(b*b) == (c*c))) {
                        //System.out.println("a="+ a + " b=" + b + " c=" + c);
                        return new Long(a*b*c);
                    }
                }
            }
        }
        return 0l;
    }

    /**
     * Problem 10: The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.
     * Find the sum of all the primes below two million.
     * @param primeRoof
     * @return
     */
    public Long primesSummation(Integer primeRoof) {
        PrimeNumbers primeNumbers = new PrimeNumbers();
        Long sum = 0l;
        Long primeNumber = primeNumbers.next();
        int counter = 0;
        while(primeNumber < primeRoof) {
            counter++;
            if(counter % 10000 == 0) {
                System.out.println("Prime: " + primeNumber + " sum=" + sum);
            }
            sum += primeNumber;
            primeNumber = primeNumbers.next();
        }

        return sum;
    }
}
