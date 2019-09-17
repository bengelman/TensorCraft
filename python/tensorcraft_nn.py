import socket
import tensorflow as tf
from tensorflow import keras
from tensorflow.keras import layers
from numpy import array

PORT = 2323

sock = socket.socket()
sock.bind(("127.0.0.1", PORT))
print("Bound to port, waiting for connection")
print(socket.gethostbyname(socket.gethostname()))
sock.listen(1)

(conn, address) = sock.accept()
cf = conn.makefile('r', 1)

print("Connection established")
try:
	connectCheck = cf.readline()
except:
	print("An error occurred");
	exit()
print("Received data")
print(connectCheck)
connectCheck = int(cf.readline())
print(connectCheck)
if connectCheck != PORT:
	exit()
print("Connection ready")
inputs = int(cf.readline())
outputs = int(cf.readline())
print("Outputs: " + str(outputs) + " Inputs: " + str(inputs))

inSamples = []
outSamples = []
index = 0
inSample = []
outSample = []
data = cf.readline()
while int(data) != -1:
	if index < inputs:
		inSample.append(data[:1])
	elif index < inputs + outputs:
		outSample.append(data[:1])
	if index == inputs + outputs -1:
		inSamples.append(inSample)
		outSamples.append(outSample)
		index = -1
		inSample = []
		outSample = []
	index += 1;
	data = cf.readline()
print(inSamples)
print(outSamples)
l0 = tf.keras.layers.Dense(units=1,input_shape=[inputs])
l1 = tf.keras.layers.Dense(units=64)
l2 = tf.keras.layers.Dense(units=4)
model = tf.keras.Sequential([l0, l1, l2])
model.compile(loss='mean_squared_error', optimizer=tf.keras.optimizers.Adam(0.1))
model.fit(array(inSamples), array(outSamples), epochs=40, verbose=True)

conn.sendall(bytes("2323\n", 'UTF-8'));
while True:
	print("Starting iteration")
	andFlag = True
	orFlag = False
	predictFrom = []
	for input in range (0, inputs):
		predictFrom.append(cf.readline()[:1])
	print(predictFrom)
	predicted = model.predict(array([predictFrom]))
	print(predicted)
	for prediction in predicted[0]:
		conn.sendall(bytes(str(int(round(prediction))) + "\n", 'UTF-8'))

