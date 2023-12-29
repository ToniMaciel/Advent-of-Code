import re

RED = 12
GREEN = 13
BLUE = 14

with open("input.txt") as f:
    lines = f.readlines()
    total = 0
    for line in lines:
        game_name, sets = line.split(':')
        set = sets.split(';')
        valid = True
        for colors in set:
            for color in colors.split(','):
                cube = color.strip()
                if cube:
                    if 'red' in cube:
                        valid = valid and (int(re.findall(r'\d+', cube)[0]) <= RED)
                    elif 'green' in cube:
                        valid = valid and (int(re.findall(r'\d+', cube)[0]) <= GREEN)
                    elif 'blue' in cube:
                        valid = valid and (int(re.findall(r'\d+', cube)[0]) <= BLUE)
        if valid:
            total += int(re.findall(r'\d+', game_name)[0])
    print(total)
