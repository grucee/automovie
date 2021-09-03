package com.grucee.gobject;

import org.apache.batik.anim.dom.SVGOMDocument;
import org.apache.batik.bridge.*;
import org.apache.batik.bridge.svg12.SVG12BridgeContext;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.gvt.RootGraphicsNode;

import java.io.IOException;

/**
 * svg图像
 */
public class AmSVG extends AbsGobject{
    /**
     * SVG GVT tree
     */
    private RootGraphicsNode svgRoot;

    /**
     * original svg path
     */
    private String svgPath;
    public AmSVG(String svgPath) {
        this.svgPath = svgPath;
        try {
            init();
        } catch (Throwable t) {
            throw new RuntimeException("parse svg error, path:" + svgPath, t);
        }
    }

    private void init() throws IOException {
        //step-1: load svg to xml document
        UserAgent userAgent = new UserAgentAdapter();
        DocumentLoader loader = new DocumentLoader(userAgent);
        SVGOMDocument svgDocument = (SVGOMDocument)loader.loadDocument(this.svgPath);

        //step-2: create context
        BridgeContext bridgeContext;
        if (svgDocument.isSVG12()) {
            bridgeContext = new SVG12BridgeContext(userAgent, loader);
        } else {
            bridgeContext = new BridgeContext(userAgent, loader);
        }

        //step-3: build the GVT tree
        GVTBuilder builder = new GVTBuilder();
        this.svgRoot = (RootGraphicsNode)builder.build(bridgeContext, svgDocument);
    }
    @Override
    public GraphicsNode getGraphicsNode() {
        return this.svgRoot;
    }
}
