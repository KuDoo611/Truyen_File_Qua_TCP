#!/usr/bin/python3
"""reducer.py: Reducer script for Hadoop MapReduce that processes word counts and aggregates them."""

import sys

def emit(word, count):
    """Hàm in từ và số lần xuất hiện."""
    if word is not None:
        print(f'{word}\t{count}')

# Khởi tạo biến để lưu từ hiện tại và số lần đếm
current_word = None
current_count = 0

# Đọc từng dòng từ stdin
for line in sys.stdin:
    # Loại bỏ khoảng trắng ở đầu và cuối
    line = line.strip()
    
    # Kiểm tra nếu dòng rỗng thì bỏ qua
    if not line:
        continue

    # Tách dòng thành cặp <word, count>
    try:
        word, count = line.split('\t', 1)
        count = int(count)
    except ValueError:
        # Nếu không hợp lệ thì bỏ qua
        continue

    # Cộng dồn giá trị count cho từ hiện tại
    if word == current_word:
        current_count += count
    else:
        # Gặp từ mới thì in kết quả từ trước đó
        emit(current_word, current_count)
        # Chuyển sang từ mới
        current_word = word
        current_count = count

# In từ cuối cùng
emit(current_word, current_count)
