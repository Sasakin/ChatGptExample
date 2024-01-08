import sys
import io
import base64
import matplotlib.pyplot as plt

symbol = sys.argv[1]

# Generate data
x = [i for i in range(100)]
y = [i * i for i in x]

# Create plot
fig, ax = plt.subplots()
ax.plot(x, y)

# Convert plot to PNG image
buffer = io.BytesIO()
plt.savefig(buffer, format='png')
img_base64 = base64.b64encode(buffer.getvalue()).decode('utf-8')

# Print image data to stdout
print(img_base64)