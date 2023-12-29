with open("input_test_v2.txt") as f:
    lines = f.readlines()
    total_sum = 0
    numbers = ['one', 'two', 'three', 'four', 'five', 'six', 'seven', 'eight', 'nine']
    map_name_number = {'one': 1, 'two': 2, 'three': 3, 'four': 4, 'five': 5,
                       'six': 6, 'seven': 7, 'eight': 8, 'nine': 9}
    for line in lines:
        line.replace('\n', '')
        left_match = float('inf')
        right_match = float('-inf')
        l = 0
        r = 0
        code = 0
        for number in numbers:
            index = line.find(number)
            index_r = line.rfind(number)
            if index != -1 and index < left_match:
                left_match = index
                l = map_name_number[number]*10
            if index != -1 and index_r > right_match:
                right_match = index_r
                r = map_name_number[number]
        for i, char in enumerate(line):
            if i > left_match:
                break
            if char >= '0' and char <= '9' and i < left_match:
                l = int(char)*10
                break
        right_match = len(line) - right_match - 1
        for i, char in enumerate(reversed(line)):
            if i > right_match:
                break
            if char >= '0' and char <= '9' and i < right_match:
                r = int(char)
                break
        total_sum += r + l
    print(total_sum)

            