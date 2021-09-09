package bowling.domain;

import bowling.domain.frame.info.FrameInfo;
import bowling.domain.frame.info.NormalFrameInfo;

public class NormalFrame implements Frame {

    private final FrameInfo frameInfo;
    private final Pins pins;

    private NormalFrame(FrameInfo normalFrameInfo, Pins pins) {
        this.frameInfo = normalFrameInfo;
        this.pins = pins;
    }

    public static NormalFrame of(FrameInfo normalFrameInfo, Pins pins) {
        return new NormalFrame(normalFrameInfo, pins);
    }

    public static NormalFrame create() {
        return new NormalFrame(NormalFrameInfo.create(), Pins.create());
    }

    @Override
    public Frame roll(int downPins) {
        Pins roll = pins.roll(downPins);

        if (isLastFrame()) {
            return FinalFrame.create();
        }

        if (hasNextFrame()) {
            return of(frameInfo.nextFrame(), Pins.create());
        }

        return of(frameInfo.nextRound(), roll);
    }

    private boolean isLastFrame() {
        return frameInfo.isEndFrame() && (frameInfo.isLastRound() || pins.isAllDown());
    }

    private boolean hasNextFrame() {
        return frameInfo.isLastRound() || pins.isAllDown();
    }

    @Override
    public int numberOfDownedPins() {
        return pins.numberOfPinDowns();
    }

    @Override
    public FrameInfo frameInfo() {
        return frameInfo;
    }

    @Override
    public boolean hasNextRound() {
        return pins.isAllDown() || frameInfo.isFirstRound();
    }

    @Override
    public Status pinStatus() {
        return pins.status();
    }

}
