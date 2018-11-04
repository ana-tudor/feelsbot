const bearSprite = {
  name:"monster",
  size: {width: 220, height: 220},
  animationTypes: ['IDLE', 'FAST', 'EH', 'WAVE'],
  frames: [
    require('./bear0.jpeg'),
    require('./bear1.jpeg'),
    require('./bear2.jpeg'),
    require('./bear3.jpeg'),
    require('./bear4.jpeg'),
    require('./bear5.jpeg'),
    require('./bear6.jpeg'),
    require('./bear7.jpeg'),
    require('./bear8.jpeg'),
    require('./bear9.jpeg'),
    require('./bear10.jpeg'),
    require('./bear11.jpeg'),
    require('./bear12.jpeg'),
    require('./bear13.jpeg'),
    require('./bear14.jpeg'),
    require('./bear15.jpeg'),
  ],
  animationIndex: function getAnimationIndex (animationType) {
    switch (animationType) {
      case 'WAVE':
        return [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15];
      case 'FAST':
        return [0,2,4,6,8,10,12,14];
      case 'EH':
        return [0,8,0,8];
      case 'IDLE':
        return [0];
    }
  },
};

export default bearSprite;