import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Card implements Comparable<Card>{
        List<Integer> hand;
        int bind;
        int type;
        
        public Card(String hand, int bind) {
            this.hand = processHand(hand);
            this.bind = bind;
            this.type = calculateType();
        }

        private List<Integer> processHand(String hand) {
            return hand.chars().mapToObj(c -> {
                switch (c) {
                    case 'A':
                        return 14;
                    case 'K':
                        return 13;
                    case 'Q':
                        return 12;
                    case 'J':
                        return 11;
                    case 'T':
                        return 10;
                    default:
                        return Character.getNumericValue(c);
                }
            }).collect(Collectors.toList());
        }

        private int calculateType() {
            Map<Integer, Integer> frequency = hand.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e -> 1)));

            switch (frequency.keySet().size()) {
                case 1:
                    return 6;
                case 2:
                    return Collections.max(frequency.values()) == 4 ? 5 : 4;
                case 3:
                    return Collections.max(frequency.values()) == 3 ? 3 : 2;
                case 4:
                    return 1;
                default:
                    return 0;
            }
        }

        @Override
        public int compareTo(Card other) {
            if (this.type != other.type) {
                return Integer.compare(this.type, other.type);
            } else {
                for (int i = 0; i < this.hand.size(); i++) {
                    int comparison = Integer.compare(this.hand.get(i), other.hand.get(i));
                    if (comparison != 0) {
                        return comparison;
                    }
                }
                return 0;
            }
        }

        
    }