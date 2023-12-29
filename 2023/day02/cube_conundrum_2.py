import re

with open("input.txt") as f:
    lines = f.readlines()
    total = 0
    for line in lines:
        game_name, sets = line.split(':')
        set = sets.split(';')
        red, green, blue = 0, 0, 0
        for colors in set:
            for color in colors.split(','):
                cube = color.strip()
                if cube:
                    if 'red' in cube:
                        red = max(red, (int(re.findall(r'\d+', cube)[0])))
                    elif 'green' in cube:
                        green = max(green, (int(re.findall(r'\d+', cube)[0])))
                    elif 'blue' in cube:
                        blue = max(blue, (int(re.findall(r'\d+', cube)[0])))
        total += red * green * blue
    print(total)
