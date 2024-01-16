//package mmlTests.a201701b.scala.gpt35turbo
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Supplier;
//
//import mmlTests.a201701b.scala.Event;
//import mmlTests.a201701b.scala.Trace;
//import mmlTests.a201701b.scala.TraceFactory;
//
//public class TraceFactoryImpl implements TraceFactory {
//
//  @Override
//  public <X> Trace<X> fromSuppliers(Supplier<Integer> sdeltaTime, Supplier<X> svalue, int size) {
//    return new Trace<X>() {
//      private int currentTime = 0;
//
//      @Override
//      public Optional<Event<X>> nextEvent() {
//        if (currentTime < size) {
//          Event<X> event = new EventImpl<>(currentTime, svalue.get());
//          currentTime += sdeltaTime.get();
//          return Optional.of(event);
//        } else {
//          return Optional.empty();
//        }
//      }
//
//      @Override
//      public Iterator<Event<X>> iterator() {
//        return new Iterator<>() {
//          private int currentIndex = 0;
//
//          @Override
//          public boolean hasNext() {
//            return currentIndex < size;
//          }
//
//          @Override
//          public Event<X> next() {
//            Event<X> event = new EventImpl<>(currentIndex, svalue.get());
//            currentIndex += sdeltaTime.get();
//            return event;
//          }
//        };
//      }
//
//      @Override
//      public void skipAfter(int time) {
//        currentTime = Math.max(currentTime, time);
//      }
//
//      @Override
//      public Trace<X> combineWith(Trace<X> trace) {
//        return new TraceImpl<>(this, trace);
//      }
//
//      @Override
//      public Trace<X> dropValues(X value) {
//        return new Trace<X>() {
//          private Iterator<Event<X>> iterator = TraceFactoryImpl.this.iterator();
//
//          @Override
//          public Optional<Event<X>> nextEvent() {
//            while (iterator.hasNext()) {
//              Event<X> event = iterator.next();
//              if (!event.getValue().equals(value)) {
//                return Optional.of(event);
//              }
//            }
//            return Optional.empty();
//          }
//
//          @Override
//          public Iterator<Event<X>> iterator() {
//            return new Iterator<>() {
//              private Iterator<Event<X>> originalIterator = TraceFactoryImpl.this.iterator();
//
//              private Event<X> currentEvent;
//
//              @Override
//              public boolean hasNext() {
//                setCurrentEvent();
//                return currentEvent != null;
//              }
//
//              @Override
//              public Event<X> next() {
//                setCurrentEvent();
//                Event<X> event = currentEvent;
//                currentEvent = null;
//                return event;
//              }
//
//              private void setCurrentEvent() {
//                if (currentEvent == null) {
//                  while (originalIterator.hasNext()) {
//                    Event<X> event = originalIterator.next();
//                    if (!event.getValue().equals(value)) {
//                      currentEvent = event;
//                      break;
//                    }
//                  }
//                }
//              }
//            };
//          }
//
//          @Override
//          public void skipAfter(int time) {
//            TraceFactoryImpl.this.skipAfter(time);
//          }
//
//          @Override
//          public Trace<X> combineWith(Trace<X> trace) {
//            return TraceFactoryImpl.this.combineWith(trace).dropValues(value);
//          }
//
//          @Override
//          public Trace<X> dropValues(X value) {
//            return this;
//          }
//        };
//      }
//    };
//    }
//
//    @Override
//    public <X> Trace<X> constant(Supplier<Integer> sdeltaTime, X value, int size) {
//      return new Trace<X>() {
//        private int currentTime = 0;
//
//        @Override
//        public Optional<Event<X>> nextEvent() {
//          if (currentTime < size) {
//            Event<X> event = new EventImpl<>(currentTime, value);
//            currentTime += sdeltaTime.get();
//            return Optional.of(event);
//          } else {
//            return Optional.empty();
//          }
//        }
//
//        @Override
//        public Iterator<Event<X>> iterator() {
//          return new Iterator<>() {
//            private int currentIndex = 0;
//
//            @Override
//            public boolean hasNext() {
//              return currentIndex < size;
//            }
//
//            @Override
//            public Event<X> next() {
//              Event<X> event = new EventImpl<>(currentIndex, value);
//              currentIndex += sdeltaTime.get();
//              return event;
//            }
//          };
//        }
//
//        @Override
//        public void skipAfter(int time) {
//          currentTime = Math.max(currentTime, time);
//        }
//
//        @Override
//        public Trace<X> combineWith(Trace<X> trace) {
//          return new TraceImpl<>(this, trace);
//        }
//
//        @Override
//        public Trace<X> dropValues(X value) {
//          return new Trace<X>() {
//            private Iterator<Event<X>> iterator = TraceFactoryImpl.this.iterator();
//
//            @Override
//            public Optional<Event<X>> nextEvent() {
//              while (iterator.hasNext()) {
//                Event<X> event = iterator.next();
//                if (!event.getValue().equals(value)) {
//                  return Optional.of(event);
//                }
//              }
//              return Optional.empty();
//            }
//
//            @Override
//            public Iterator<Event<X>> iterator() {
//              return new Iterator<>() {
//                private Iterator<Event<X>> originalIterator = TraceFactoryImpl.this.iterator();
//
//                private Event<X> currentEvent;
//
//                @Override
//                public boolean hasNext() {
//                  setCurrentEvent();
//                  return currentEvent != null;
//                }
//
//                @Override
//                public Event<X> next() {
//                  setCurrentEvent();
//                  Event<X> event = currentEvent;
//                  currentEvent = null;
//                  return event;
//                }
//
//                private void setCurrentEvent() {
//                  if (currentEvent == null) {
//                    while (originalIterator.hasNext()) {
//                      Event<X> event = originalIterator.next();
//                      if (!event.getValue().equals(value)) {
//                        currentEvent = event;
//                        break;
//                      }
//                    }
//                  }
//                }
//              };
//            }
//
//            @Override
//            public void skipAfter(int time) {
//              TraceFactoryImpl.this.skipAfter(time);
//            }
//
//            @Override
//            public Trace<X> combineWith(Trace<X> trace) {
//              return TraceFactoryImpl.this.combineWith(trace).dropValues(value);
//            }
//
//            @Override
//            public Trace<X> dropValues(X value) {
//              return this;
//            }
//          };
//        }
//      };
//      }
//
//      @Override
//      public <X> Trace<X> discrete(Supplier<X> svalue, int size) {
//        return new Trace<X>() {
//          private int currentTime = 0;
//
//          @Override
//          public Optional<Event<X>> nextEvent() {
//            if (currentTime < size) {
//              Event<X> event = new EventImpl<>(currentTime, svalue.get());
//              currentTime++;
//              return Optional.of(event);
//            } else {
//              return Optional.empty();
//            }
//          }
//
//          @Override
//          public Iterator<Event<X>> iterator() {
//            return new Iterator<>() {
//              private int currentIndex = 0;
//
//              @Override
//              public boolean hasNext() {
//                return currentIndex < size;
//              }
//
//              @Override
//              public Event<X> next() {
//                Event<X> event = new EventImpl<>(currentIndex, svalue.get());
//                currentIndex++;
//                return event;
//              }
//            };
//          }
//
//          @Override
//          public void skipAfter(int time) {
//            currentTime = Math.max(currentTime, time);
//          }
//
//
