with open("input.txt") as f:
    lines = f.readlines()
    total_sum = 0
    for line in lines:
        code = 0
        for char in line:
            if char >= '0' and char <= '9':
                code = int(char)*10
                break
        for char in reversed(line):
            if char >= '0' and char <= '9':
                code += int(char)
                break
        total_sum += code
    print(total_sum)