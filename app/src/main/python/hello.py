import cv2
import numpy as np
import os
from matplotlib import pyplot as plt
12222222222222222
##创建新文件夹，如果该文件已经存在，则报错
# os.makedirs('./hhh')
img=cv2.imread('./1.jpg')
##因为opencv读取的图片是BGR格式
# img=cv2.cvtColor(img,cv2.COLOR_BGR2RGB)
plt.imshow(cv2.cvtColor(img,cv2.COLOR_BGR2RGB))
plt.show()

basename=os.path.basename('./1.jpg')

##裁剪块的大小
patchsize=96
##裁剪的数量
num_patches=10

h,w,_=img.shape

for j in range(num_patches):
    rr=np.random.randint(0,h-patchsize)
    cc=np.random.randint(0,w-patchsize)
    patchs=img[rr:rr+patchsize,cc:cc+patchsize,:]
    cv2.imwrite(os.path.join('./hhh/',basename.split('.')[0]+'{}.png'.format(j)),patchs)