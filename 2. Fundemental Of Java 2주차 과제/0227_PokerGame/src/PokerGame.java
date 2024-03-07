import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class User {
    static int userCount = 0;
    int userNumber;
    Card[] cards;
    int[] countDenomination;
    int myRanking; // 유저들 사이에서 몇 등인지 알려준다. (User들끼리 Sorting이 가능하다는 소리)
    int[] myResult; // 투페어라면 어떤 투페어인지, 원페어라면 어떤 원페어인지 확인한다.

    User() {
        userNumber = ++userCount;
        cards = new Card[5];
        countDenomination = new int[13];
        myResult = new int[5];
    }

    public void printCards() {
        System.out.println("===========User Number [" + this.userNumber + "]'s Card List===========");
        for (int i = 0; i < cards.length; i++) {
            System.out.println("Card [" + (i + 1) + "] " + cards[i]);
        }
        System.out.println("=================================================");
    }

    public void countDenominationOfCard() {
        int denominationOfCard;
        for (int i = 0; i < cards.length; i++) {
            denominationOfCard = cards[i].denomination.getValue();
            countDenomination[denominationOfCard]++;
        }
    }

    public int getMyRanking() { // 유저들 사이에서의 나의 랭킹을 알아야 함.
        return 0;
    }

    public String getmyResult() { // 나의 결과를 받을 수 있어야 함.
        return null;
    }
}

class CardSet {
    List<Card> cardList;

    CardSet() {
        cardList = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Denomination rank : Denomination.values()) {
                cardList.add(new Card(rank, suit));
            }
        }
    }

    public List<Card> shuffle(CardSet cardSet) {
        List<Card> cards = cardSet.cardList; // CardSet에서 카드 리스트를 가져옵니다.
        Collections.shuffle(cards); // Collections의 shuffle 메서드를 사용해 카드 리스트를 섞습니다.
        return cards; // 섞인 카드 리스트를 반환합니다.
    }

    public Card[] getFiveCards() {
        Card[] cardArray = new Card[5];
        
        for (int i = 0; i < cardArray.length; i++) { // 다섯 개 받아내고
            cardArray[i] = cardList.get(i);
        }

        for (int i = 0; i < cardArray.length; i++) { // 다섯 개 지우고
            cardList.remove(0);
        }

        return cardArray;
    }
}

enum Denomination {
    TWO(1),
    THREE(2),
    FOUR(3),
    FIVE(4),
    SIX(5),
    SEVEN(6),
    EIGHT(7),
    NINE(8),
    TEN(9),
    JACK(10),
    QUEEN(11),
    KING(12),
    ACE(13);

    private int value;

    Denomination(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

enum Suit {
    SPADE,
    DIAMOND,
    HEART,
    CLUB
}

class Card { // 문양, 숫자
    Denomination denomination;
    Suit suit;

    Card() {
        denomination = Denomination.ACE;
        suit = Suit.SPADE;
    }

    Card(Denomination denomination, Suit suit) {
        this.denomination = denomination;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return "[Card] - Denomination : " + this.denomination + ", Suit : " + this.suit;
    }
}

public class PokerGame {
    public static void main(String[] args) throws IOException {
        // 1. 사용자에게 참여하는 Player의 수를 입력 받는다. // 완료
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Input the number of player : ");
        int numberOfPlayer = Integer.parseInt(br.readLine());

        if (numberOfPlayer > 10) {
            System.out.println("52장으로는 " + numberOfPlayer + "명이서는 카드가 부족해서 파이프 포커 못해요ㅠ");
            System.out.println("최대 10명까지만 Five Poker을 진행할 수 있습니다.");
            System.out.println("프로그램을 종료합니다.");
            return;
        }

        // 2. 입력 받은 수에 따라 User 객체를 생성한다.
        User[] users = new User[numberOfPlayer];
        for (int i = 0; i < users.length; i++) {
            users[i] = new User();
        }

        // 3. 카드를 52장 만들고, 랜덤하게 순서를 섞는다. => Collections.shuffle();
        CardSet cardSet = new CardSet();
        cardSet.shuffle(cardSet);

        // 4. User은 인당 카드를 다섯 장 씩 받는다.
        for (int i = 0; i < users.length; i++) {
            users[i].cards = cardSet.getFiveCards();
        }

        // 테스트 코드 : 유저 별로 카드를 출력함. - 여기까지는 이상 없다.
        for (int i = 0; i < users.length; i++) {
            users[i].printCards();
        }

        // 5. 승부를 가린다. - 이건 유저도, 카드 셋에서도 해줄 게 아님. 메인 클래스에서 해줘야 함.
        competiton(users);

        // 6. 누가 이겼는지 결과를 출력한다. - 이것도 
        printWhoIsWinner(users);
    }

    private static void competiton(User[] users) {
        /*
         * 자, Enum을 쓰고 자시고 승부를 어떻게 가릴래?
         * 
         * 1. 원페어 투페어만 승부를 가리면 돼. 그 이상은 너무 어려운 일이야.
         * 2. 그런 상황에서, 숫자만 받아 오면 돼. 무늬는 필요가 없어.
         * 3. 역정렬된 int[5]로 결과를 받아 내자.
         * 4. 하이 => 제일 뒤에 있는 원소 빼 오면 될 일이고
         * 5. 원페어 => 중복되는 한 쌍의 원소 빼오면 될 일이고
         * 6. 투페어 => 중복되는 두 쌍의 원소 빼오면 될 일이야.
         * 7. 근데 이 결과를 어떻게 비교해? (투페어 = 2, 원페어 = 1, 하이 = 0)
         * == 그러면 일단 여기까지 하자 ==
         * 
         * 8. 투페어 => 큰 페어, 작은 페어, 키커
         * 9. 원페어 => 페어, 키커, 나머지 세 장
         * 10. 하이 => 다섯 장 크기 순
         * 
         * myResult는 int[]로 구현하는 게 합리적이겠다.
         */


        // 1. int[5]로 결과를 저장했다.
        for (int i = 0; i < users.length; i++) {
            
            for (int j = 0; j < users[i].cards.length; j++) {
                users[i].myResult[j] = users[i].cards[j].denomination.getValue();
            }
            // 2. 정렬했다.
            Arrays.sort(users[i].myResult);

            // 3. 투페어, 원페어, 하이를 가리자.
            if(isTwoPair(users[i].myResult)) {
                users[i].myRanking = 1;
            } else if(isOnePair(users[i].myResult)) {
                users[i].myRanking = 2;
            } else {
                users[i].myRanking = 3;
            }
        }
    }

    private static boolean isTwoPair(int[] myResult) {

        // 1. 테이블에 숫자 별로 카운트 기록
        int[] table = new int[13];
        int[] intArr = new int[myResult.length];
        intArr = myResult;
        for (int i = 0; i < intArr.length; i++) {
            table[--intArr[i]]++;
        }

        System.out.println(Arrays.toString(table));
        
        // 2. 카운트 횟수 == 페어 수
        int cnt = 0;
        for (int i = 0; i < table.length; i++) {
            if(table[i] >= 2) {
                cnt++;
            }
        }

        // 3. 투페어면 return true;
        if(cnt == 2) {
            return true;
        } else {
            return false;
        }
    }


    private static boolean isOnePair(int[] myResult) {

        // 1. 테이블에 숫자 별로 카운트 기록
        int[] table = new int[13];
        int[] intArr = new int[myResult.length];
        intArr = myResult;
        for (int i = 0; i < intArr.length; i++) {
            table[intArr[i]]++;
        }

        
        // 2. 카운트 횟수 == 페어 수
        int cnt = 0;
        for (int i = 0; i < table.length; i++) {
            if(table[i] >= 2) {
                cnt++;
            }
        }

        // 3. 투페어면 return true;
        if(cnt == 1) {
            return true;
        } else {
            return false;
        }
    }


    private static void printWhoIsWinner(User[] users) {
        for (int i = 0; i < users.length; i++) {
            if(users[i].myRanking == 1) {
                System.out.println("User [" + users[i].userNumber + "] : 투페어");
            } else if(users[i].myRanking == 2) {
                System.out.println("User [" + users[i].userNumber + "] : 원페어");
            } else if(users[i].myRanking == 3) {
                System.out.println("User [" + users[i].userNumber + "] : 하이카드");
            } else {
                System.out.println("비정상");
            }
        }
    }
}